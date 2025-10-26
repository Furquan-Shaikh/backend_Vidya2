package edu.js.project.controller;

import edu.js.project.dto.StudentExitSurveyDTO;
import edu.js.project.dto.TeacherFeedbackDTO;
import edu.js.project.dto.AnalyticsDTO;
import edu.js.project.service.impl.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/VidyaSarthi")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping("/student/feedback/teacher")
    public ResponseEntity<?> submitTeacherFeedback(@RequestBody Map<String, Object> request) {
        try {
            Object studentIdObj = request.get("studentId");
            if (studentIdObj == null) {
                return ResponseEntity.badRequest().body(
                        Map.of("success", false, "message", "studentId is required")
                );
            }

            String studentId;
            try {
                if (studentIdObj instanceof Number) {
                    studentId = String.valueOf((Integer) studentIdObj);
                } else {
                    studentId = String.valueOf(studentIdObj);
                }
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().body(
                        Map.of("success", false, "message", "Invalid studentId format")
                );
            }

            TeacherFeedbackDTO dto = buildTeacherFeedbackDTO(request);

            Map<String, Object> response = feedbackService.submitTeacherFeedback(studentId, dto);

            if ((Boolean) response.get("success")) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                    Map.of("success", false, "message", e.getMessage())
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Internal server error: " + e.getMessage()));
        }
    }

    @PostMapping("/student/exit-survey")
    public ResponseEntity<?> submitExitSurvey(@RequestBody Map<String, Object> request) {
        try {
            Object studentIdObj = request.get("studentId");
            if (studentIdObj == null) {
                return ResponseEntity.badRequest().body(
                        Map.of("success", false, "message", "studentId is required")
                );
            }

            String studentId;
            try {
                if (studentIdObj instanceof Number) {
                    studentId = String.valueOf((Integer) studentIdObj);
                } else {
                    studentId = String.valueOf(studentIdObj);
                }

            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().body(
                        Map.of("success", false, "message", "Invalid studentId format")
                );
            }

            StudentExitSurveyDTO dto = buildExitSurveyDTO(request);

            Map<String, Object> response = feedbackService.submitExitSurvey(studentId, dto);

            if ((Boolean) response.get("success")) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                    Map.of("success", false, "message", e.getMessage())
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Internal server error: " + e.getMessage()));
        }
    }

    @GetMapping("/student/exit-survey/status/{studentId}")
    public ResponseEntity<?> checkExitSurveyStatus(@PathVariable String studentId) {
        try {
            String id = studentId;
            Map<String, Object> response = feedbackService.checkExitSurveyStatus(id);

            return ResponseEntity.ok(response);

        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(
                    Map.of("success", false, "message", "Invalid studentId format")
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Internal server error"));
        }
    }

    @GetMapping("/admin/analytics/teachers")
    public ResponseEntity<?> getAllTeachersAnalytics() {
        try {
            List<AnalyticsDTO.FacultyOverview> overview = feedbackService.getAllFacultyOverview();

            return ResponseEntity.ok(overview);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Internal server error"));
        }
    }

    @GetMapping("/admin/analytics/teacher/{facultyId}")
    public ResponseEntity<?> getTeacherDetailedAnalytics(@PathVariable Long facultyId) {
        try {
            AnalyticsDTO.FacultyDetailedStats stats = feedbackService.getFacultyDetailedStats(facultyId);

            return ResponseEntity.ok(stats);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Internal server error"));
        }
    }

    @GetMapping("/admin/reviews/teacher/{facultyId}")
    public ResponseEntity<?> getTeacherComments(
            @PathVariable Long facultyId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit) {

        try {
            Page<AnalyticsDTO.FacultyComment> comments = feedbackService.getFacultyComments(facultyId, page, limit);

            return ResponseEntity.ok(comments);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Internal server error"));
        }
    }

    @GetMapping("/admin/analytics/student-survey")
    public ResponseEntity<?> getExitSurveyAnalytics() {
        try {
            AnalyticsDTO.ExitSurveyStats stats = feedbackService.getExitSurveyStats();

            return ResponseEntity.ok(stats);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Internal server error"));
        }
    }

    @GetMapping("/admin/reviews/student-survey")
    public ResponseEntity<?> getExitSurveyComments(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit) {

        try {
            Page<AnalyticsDTO.ExitSurveyComment> comments = feedbackService.getExitSurveyComments(page, limit);

            return ResponseEntity.ok(comments);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Internal server error"));
        }
    }

    private TeacherFeedbackDTO buildTeacherFeedbackDTO(Map<String, Object> request) {
        TeacherFeedbackDTO dto = new TeacherFeedbackDTO();

        dto.setCourseId((String) request.get("courseId"));
        dto.setRegulation((String) request.get("regulation"));
        dto.setBranch((String) request.get("branch"));

        Object semesterObj = request.get("semester");
        if (semesterObj instanceof String) {
            dto.setSemester((String) semesterObj);
        } else if (semesterObj instanceof Number) {
            dto.setSemester(String.valueOf(semesterObj));
        }

        Object facultyIdObj = request.get("facultyId");
        if (facultyIdObj != null) {
            if (facultyIdObj instanceof Number) {
                dto.setFacultyId(((Number) facultyIdObj).longValue());
            } else {
                dto.setFacultyId(Long.valueOf(String.valueOf(facultyIdObj)));
            }
        }

        Object ratingObj = request.get("overallRating");
        if (ratingObj instanceof Number) {
            dto.setOverallRating(((Number) ratingObj).intValue());
        }

        dto.setAdditionalComments((String) request.get("additionalComments"));

        @SuppressWarnings("unchecked")
        List<Map<String, String>> feedbackList = (List<Map<String, String>>) request.get("feedback");
        if (feedbackList != null) {
            List<TeacherFeedbackDTO.FeedbackQuestion> questions = feedbackList.stream()
                    .map(f -> TeacherFeedbackDTO.FeedbackQuestion.builder()
                            .question(f.get("question"))
                            .option(f.get("option"))
                            .build())
                    .collect(java.util.stream.Collectors.toList());
            dto.setFeedback(questions);
        }

        return dto;
    }

    private StudentExitSurveyDTO buildExitSurveyDTO(Map<String, Object> request) {
        StudentExitSurveyDTO dto = new StudentExitSurveyDTO();

        Object ratingObj = request.get("overallRating");
        if (ratingObj instanceof Number) {
            dto.setOverallRating(((Number) ratingObj).intValue());
        }

        dto.setAdditionalComments((String) request.get("additionalComments"));

        @SuppressWarnings("unchecked")
        List<Map<String, String>> feedbackList = (List<Map<String, String>>) request.get("feedback");
        if (feedbackList != null) {
            List<StudentExitSurveyDTO.FeedbackItem> items = feedbackList.stream()
                    .map(f -> StudentExitSurveyDTO.FeedbackItem.builder()
                            .question(f.get("question"))
                            .option(f.get("option"))
                            .build())
                    .collect(java.util.stream.Collectors.toList());
            dto.setFeedback(items);
        }

        return dto;
    }
}
