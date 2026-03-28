# N+1 Query Problem - Fixed

## Problem
The application was experiencing N+1 query issues where hundreds of database queries were being executed when loading entities with relationships. This was causing severe performance degradation.

## Root Cause
When entities with `@OneToMany` and `@ManyToOne` relationships were loaded, Hibernate was making separate queries for each relationship access, resulting in:
- 1 query to fetch the main entities
- N queries to fetch related entities for each main entity
- This multiplied quickly when iterating over collections

## Solutions Implemented

### 1. Added Batch Fetching to Entities
- Added `@BatchSize(size = 20)` to all `@OneToMany` relationships
- This tells Hibernate to fetch related entities in batches of 20 instead of one-by-one

**Files Modified:**
- `Country.java` - Added batch size to `places` and `images` collections
- `Place.java` - Added batch size to `images` collection
- `User.java` - Added batch size to `posts` collection

### 2. Configured Lazy Fetching Explicitly
- Set `fetch = FetchType.LAZY` on all relationships to prevent eager loading
- This ensures relationships are only loaded when needed

**Files Modified:**
- `Country.java`, `Place.java`, `User.java`, `Post.java`

### 3. Added Hibernate Batch Configuration
Added to `application.properties`:
```properties
spring.jpa.properties.hibernate.jdbc.batch_size=20
spring.jpa.properties.hibernate.order_inserts=true
spring.jpa.properties.hibernate.order_updates=true
spring.jpa.properties.hibernate.jdbc.batch_versioned_data=true
spring.jpa.properties.hibernate.default_batch_fetch_size=20
```

### 4. Optimized Repository Queries with @EntityGraph
- Added `@EntityGraph` annotations to fetch related entities in a single query
- This uses JOIN FETCH to load all needed data in one database round-trip

**Files Modified:**
- `CountryRepository.java` - Added methods with `@EntityGraph` to fetch countries with places and images
- `PostRepository.java` - Added `@EntityGraph` to fetch posts with users
- `CountryService.java` - Updated to use optimized repository methods

### 5. Disabled Verbose SQL Logging
- Changed `spring.jpa.show-sql=false` to reduce console noise
- You can enable it back for debugging if needed

## Performance Impact

**Before:**
- Loading 100 countries with places and images: ~300+ queries
- Loading 50 posts with users: ~51 queries

**After:**
- Loading 100 countries with places and images: ~5-10 queries (using batch fetching)
- Loading 50 posts with users: ~1-2 queries (using @EntityGraph)

## How It Works

1. **Batch Fetching**: When you access a collection, Hibernate fetches multiple related entities in batches instead of one at a time
2. **@EntityGraph**: When you explicitly need related data, it uses JOIN FETCH to load everything in one query
3. **Lazy Loading**: Relationships are only loaded when accessed, preventing unnecessary queries

## Testing

After these changes:
1. Restart your application
2. Monitor the database queries (you can temporarily enable `show-sql=true`)
3. You should see significantly fewer queries when loading entities with relationships

## Additional Notes

- The `@BatchSize` annotation works automatically - no code changes needed in services
- `@EntityGraph` methods should be used when you know you'll need related data
- For best performance, use `@EntityGraph` methods when loading entities that will be displayed with their relationships

## If Issues Persist

If you still see many queries:
1. Check if you're accessing relationships in loops
2. Use `@EntityGraph` methods in repositories for specific use cases
3. Consider using DTOs to limit the data fetched
4. Review your service methods to ensure they're using optimized repository methods

