INSERT INTO batches (start_date, end_date) VALUES
('2023-01-15', '2023-04-15'),
('2023-02-20', '2023-05-20'),
('2023-03-10', '2023-06-10'),
('2023-04-05', '2023-07-05');

INSERT INTO candidates (name, email, phone_number) VALUES
('John Smith', 'john.smith@example.com', '1234567890'),
('Emily Johnson', 'emily.j@example.com', '2345678901'),
('Michael Brown', 'michael.b@example.com', '3456789012'),
('Sarah Davis', 'sarah.d@example.com', '4567890123'),
('David Wilson', 'david.w@example.com', '5678901234'),
('Jessica Lee', 'jessica.l@example.com', '6789012345');

INSERT INTO trainers (name, email, phone, joined_date) VALUES
('Robert Taylor', 'robert.t@example.com', '7890123456', '2022-01-10'),
('Jennifer Martin', 'jennifer.m@example.com', '8901234567', '2022-03-15'),
('William Anderson', 'william.a@example.com', '9012345678', '2022-05-20'),
('Lisa Thomas', 'lisa.t@example.com', '0123456789', '2022-07-25');

INSERT INTO courses (course_name, description) VALUES
('Full Stack Development', 'Comprehensive web development course covering frontend and backend technologies'),
('Data Science Fundamentals', 'Introduction to data analysis, visualization, and machine learning'),
('Cloud Computing', 'AWS, Azure, and Google Cloud platform training'),
('DevOps Engineering', 'CI/CD pipelines, containerization, and infrastructure as code');

INSERT INTO course_mapping (course_id, batch_id) VALUES
(1, 1),
(1, 2),
(2, 3),
(3, 4),
(4, 1),
(4, 3);

INSERT INTO topics (course_id, topic_name) VALUES
(1, 'HTML & CSS Fundamentals'),
(1, 'JavaScript Programming'),
(1, 'React Framework'),
(1, 'Node.js Backend'),
(2, 'Python for Data Science'),
(2, 'Pandas & NumPy'),
(2, 'Data Visualization'),
(3, 'AWS EC2 & S3'),
(3, 'Azure Services'),
(4, 'Docker & Kubernetes'),
(4, 'Jenkins CI/CD'),
(4, 'Terraform');

INSERT INTO batch_enrollments (batch_id, candidate_id, enrollment_date, status) VALUES
(1, 1, '2023-01-10', 'Completed'),
(1, 2, '2023-01-10', 'Completed'),
(1, 3, '2023-01-10', 'Terminated'),
(2, 4, '2023-02-15', 'In Progress'),
(2, 5, '2023-02-15', 'In Progress'),
(3, 6, '2023-03-05', 'In Progress'),
(4, 1, '2023-04-01', 'In Progress'),
(4, 3, '2023-04-01', 'In Progress');

INSERT INTO batch_trainers (batch_id, trainer_id) VALUES
(1, 1),
(1, 2),
(2, 1),
(3, 3),
(4, 4),
(4, 2);

INSERT INTO assignments (batch_id, title, description, due_date, max_score) VALUES
(1, 'HTML/CSS Project', 'Build a responsive portfolio website', '2023-02-10', 100),
(1, 'JavaScript Quiz', 'Basic JavaScript concepts assessment', '2023-03-01', 50),
(2, 'React Todo App', 'Create a todo application with React', '2023-03-15', 100),
(3, 'Data Analysis', 'Analyze dataset and create visualizations', '2023-04-20', 150),
(4, 'AWS Deployment', 'Deploy a web application on AWS', '2023-05-10', 200);

INSERT INTO assignment_submissions (assignment_id, candidate_id, submission_date, score) VALUES
(1, 1, '2023-02-08 14:30:00', 92.5),
(1, 2, '2023-02-09 10:15:00', 85.0),
(1, 3, '2023-02-10 23:55:00', 45.0),
(2, 1, '2023-02-28 09:20:00', 48.5),
(2, 2, '2023-03-01 08:45:00', 42.0),
(3, 4, '2023-03-14 16:30:00', 78.0),
(3, 5, '2023-03-12 11:20:00', 91.5),
(4, 6, '2023-04-18 13:10:00', 135.0),
(5, 1, '2023-05-08 15:45:00', 185.0);