
create table batches(
batch_id int primary key auto_increment,
start_date date,
end_date date
);

create table candidates(
candidate_id int primary key auto_increment,
name varchar(50) not null,
email varchar(50) not null,
phone_number varchar(15)
);

CREATE TABLE trainers (
    trainer_id int auto_increment primary key,
    name varchar(50) not null,
    email VARCHAR(100) not null,
    phone VARCHAR(15),
    joined_date date
);

CREATE TABLE courses (
    course_id INT AUTO_INCREMENT PRIMARY KEY,
    course_name VARCHAR(100) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

create table course_mapping(
	course_id INT,
    batch_id INT,
    FOREIGN KEY(course_id) REFERENCES courses(course_id) ON DELETE CASCADE,
    FOREIGN KEY(batch_id) REFERENCES batches(batch_id) ON DELETE CASCADE
);

CREATE TABLE topics (
    topic_id INT AUTO_INCREMENT PRIMARY KEY,
    course_id INT NOT NULL,
    topic_name VARCHAR(100) NOT NULL,
    FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE CASCADE
);

CREATE TABLE batch_enrollments (
    enrollment_id INT AUTO_INCREMENT PRIMARY KEY,
    batch_id INT NOT NULL,
    candidate_id INT NOT NULL,
    enrollment_date DATE NOT NULL,
    status ENUM('In Progress', 'Completed', 'Terminated') DEFAULT 'In Progress',
    FOREIGN KEY (batch_id) REFERENCES batches(batch_id),
    FOREIGN KEY (candidate_id) REFERENCES candidates(candidate_id)
);


CREATE TABLE batch_trainers (
    batch_id INT NOT NULL,
    trainer_id INT NOT NULL,
    PRIMARY KEY (batch_id, trainer_id),
    FOREIGN KEY (batch_id) REFERENCES batches(batch_id) ON DELETE CASCADE,
    FOREIGN KEY (trainer_id) REFERENCES trainers(trainer_id) ON DELETE CASCADE
);

CREATE TABLE assignments (
    assignment_id INT AUTO_INCREMENT PRIMARY KEY,
    batch_id INT NOT NULL,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    due_date DATE NOT NULL,
    max_score INT DEFAULT 100,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (batch_id) REFERENCES batches(batch_id) ON DELETE CASCADE
);

CREATE TABLE assignment_submissions (
    submission_id INT AUTO_INCREMENT PRIMARY KEY,
    assignment_id INT NOT NULL,
    candidate_id INT NOT NULL,
    submission_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    score DECIMAL(5,2),
    FOREIGN KEY (assignment_id) REFERENCES assignments(assignment_id) ON DELETE CASCADE,
    FOREIGN KEY (candidate_id) REFERENCES candidates(candidate_id) ON DELETE CASCADE
);

