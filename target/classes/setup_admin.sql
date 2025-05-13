-- Create admins table if it doesn't exist
CREATE TABLE IF NOT EXISTS admins (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert default admin account (username: admin, password: admin123)
INSERT INTO admins (username, password) 
SELECT 'admin', 'admin123'
WHERE NOT EXISTS (
    SELECT 1 FROM admins WHERE username = 'admin'
); 