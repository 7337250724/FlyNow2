-- SQL script to add seasons column to countries table
-- Run this script in your database to add the seasons column

ALTER TABLE countries 
ADD COLUMN seasons VARCHAR(200) NULL;

-- Also add seasons column to travel_packages table if it doesn't exist
ALTER TABLE travel_packages 
ADD COLUMN seasons VARCHAR(200) NULL;

