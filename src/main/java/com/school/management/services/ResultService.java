package com.school.management.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.school.management.models.Course;
import com.school.management.models.User;
import com.school.management.repository.ResultRepository;
import com.school.management.utils.updatables.ResultUpdatable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.management.models.Result;

@Service
public class ResultService {

    @Autowired
    private ResultRepository resultRepository;

    public Result createResult(Result result) {
        result.setCreatedAt(LocalDateTime.now());
        result.setUpdatedAt(LocalDateTime.now());
        return resultRepository.save(result);
    }

    public Result updateResult(Long id, ResultUpdatable resultUpdatable) {
        Result existingResult = resultRepository.findById(id).orElse(null);
        if (existingResult == null) {
            return null;
        }
        if (resultUpdatable == null) return null;

        if (resultUpdatable.getAssignmentOne() != null)
            existingResult.setAssignmentOne(resultUpdatable.getAssignmentOne());

        if (resultUpdatable.getAssignmentTwo()!= null)
            existingResult.setAssignmentTwo(resultUpdatable.getAssignmentTwo());

        if (resultUpdatable.getTestOne()!= null)
            existingResult.setTestOne(resultUpdatable.getTestOne());

        if (resultUpdatable.getTestTwo() != null)
            existingResult.setTestTwo(resultUpdatable.getTestTwo());

        if (resultUpdatable.getFinalExam() != null)
            existingResult.setFinalExam(resultUpdatable.getFinalExam());

        if (resultUpdatable.getGrade() > 0)
            existingResult.setGrade(resultUpdatable.getGrade());

        existingResult.setUpdatedAt(LocalDateTime.now());

        return resultRepository.save(existingResult);


    }

    public boolean deleteResultById(Long id) {
        if (resultRepository.existsById(id)) {
            resultRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Result findResultById(Long id) {
        return resultRepository.findById(id).orElse(null);
    }

    public List<Result> getAllResults() {
        List<Result> results = new ArrayList<>();
        resultRepository.findAll().forEach(results::add);
        return results;
    }

    public List<Result> getResultsByUserAndGrade(User user, int grade) {
        List<Result> results = new ArrayList<>();
        resultRepository.findResultByUserIdAndGrade(user, grade).forEach(results::add);
        return results;
    }

    public Result getResultByUserAndCourse(User user, Course course) {
        return resultRepository.findResultByUserAndCourse(user, course).orElse(null);
    }
}