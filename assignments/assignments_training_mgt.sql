-- Get all batches a candidate is enrolled in, with their status.
select candidate_id, batch_id, status from batch_enrollments order by candidate_id;

-- Get all trainers assigned to a batch.
select bt.trainer_id, name,bt.batch_id from batch_trainers bt join trainers t on bt.trainer_id = t.trainer_id;

-- Get all topics under a course.
select c.course_id, course_name, topic_name from topics t join courses c on t.course_id = c.course_id;


-- List assignment scores for a candidate in a batch.
select c.candidate_id, c.name,b.batch_id, asub.score from assignment_submissions as asub 
join batch_enrollments b on asub.candidate_id = b.candidate_id 
join candidates c on c.candidate_id = asub.candidate_id order by b.batch_id;

-- List candidates with status "Completed" in a given batch.
select c.name, be.batch_id, be.status from batch_enrollments be join candidates c on be.candidate_id = c.candidate_id
where be.status = "Completed";