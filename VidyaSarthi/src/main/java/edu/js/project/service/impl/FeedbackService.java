
package edu.js.project.service.impl;

import edu.js.project.dto.AnalyticsDTO;
import edu.js.project.dto.StudentExitSurveyDTO;
import edu.js.project.dto.TeacherFeedbackDTO;
import edu.js.project.entity.StudentExitSurvey;
import edu.js.project.entity.TeacherFeedback;
import edu.js.project.entity.Users;
import edu.js.project.entity.Student;
import edu.js.project.NewEntities.NewTeacher;
import edu.js.project.NewEntities.NewSubject;
import edu.js.project.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedbackService {

    private final TeacherFeedbackRepository teacherFeedbackRepository;
    private final StudentExitSurveyRepository exitSurveyRepository;
    private final UserRepository usersRepository;
    private final NewTeacherRepo teacherRepository;
    private final StudentRepository studentRepository;


    @Transactional
    public Map<String, Object> submitTeacherFeedback(String studentId, TeacherFeedbackDTO dto) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (teacherFeedbackRepository.existsByStudentIdAndCourseId(studentId, dto.getCourseId())) {
                response.put("success", false);
                response.put("message", "You have already submitted feedback for this course.");
                return response;
            }

            TeacherFeedback feedback = mapDtoToEntity(studentId, dto);
            TeacherFeedback saved = teacherFeedbackRepository.save(feedback);

            response.put("success", true);
            response.put("message", "Feedback submitted successfully!");
            response.put("feedbackId", saved.getId());

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to submit feedback: " + e.getMessage());
        }

        return response;
    }

    private TeacherFeedback mapDtoToEntity(String studentId, TeacherFeedbackDTO dto) {

        Map<String, Integer> optionMap = Map.of(
                "Good", 3,
                "Average", 2,
                "Not Good", 1
        );

        List<Integer> ratings = dto.getFeedback().stream()
                .map(q -> optionMap.getOrDefault(q.getOption(), 0))
                .collect(Collectors.toList());

        String facultyName = "Unknown";
        String subjectName = dto.getCourseId();

        try {
            Optional<NewTeacher> teacher = teacherRepository.findById(Math.toIntExact(dto.getFacultyId()));
            if (teacher.isPresent()) {
                facultyName = teacher.get().getName();
            }
        } catch (Exception e) {
            log.warn("Could not fetch faculty name: {}", e.getMessage());
        }

        return TeacherFeedback.builder()
                .studentId(studentId)
                .facultyId(dto.getFacultyId())
                .courseId(dto.getCourseId())
                .subjectName(subjectName)
                .facultyName(facultyName)
                .regulation(dto.getRegulation())
                .branch(dto.getBranch())
                .semester(dto.getSemester())
                .ratingClarity(ratings.size() > 0 ? ratings.get(0) : 0)
                .ratingPunctuality(ratings.size() > 1 ? ratings.get(1) : 0)
                .ratingHelpfulness(ratings.size() > 2 ? ratings.get(2) : 0)
                .ratingFairness(ratings.size() > 3 ? ratings.get(3) : 0)
                .ratingPreparation(ratings.size() > 4 ? ratings.get(4) : 0)
                .ratingCommand(ratings.size() > 5 ? ratings.get(5) : 0)
                .ratingPace(ratings.size() > 6 ? ratings.get(6) : 0)
                .ratingTeachingAids(ratings.size() > 7 ? ratings.get(7) : 0)
                .ratingEngagement(ratings.size() > 8 ? ratings.get(8) : 0)
                .ratingInteraction(ratings.size() > 9 ? ratings.get(9) : 0)
                .ratingSyllabusCoverage(ratings.size() > 10 ? ratings.get(10) : 0)
                .ratingEvaluation(ratings.size() > 11 ? ratings.get(11) : 0)
                .ratingAssignments(ratings.size() > 12 ? ratings.get(12) : 0)
                .ratingDoubtHandling(ratings.size() > 13 ? ratings.get(13) : 0)
                .ratingAvailability(ratings.size() > 14 ? ratings.get(14) : 0)
                .ratingProfessionalism(ratings.size() > 15 ? ratings.get(15) : 0)
                .overallRating(dto.getOverallRating())
                .commentsText(dto.getAdditionalComments())
                .build();
    }

    @Transactional
    public Map<String, Object> submitExitSurvey(String studentId, StudentExitSurveyDTO dto) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (exitSurveyRepository.existsByStudentId(studentId)) {
                response.put("success", false);
                response.put("message", "You have already submitted the exit survey.");
                return response;
            }

            StudentExitSurvey survey = mapExitSurveyDtoToEntity(studentId, dto);
            StudentExitSurvey saved = exitSurveyRepository.save(survey);

            response.put("success", true);
            response.put("message", "Exit survey submitted successfully!");
            response.put("surveyId", saved.getId());

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to submit survey: " + e.getMessage());
        }

        return response;
    }

    private StudentExitSurvey mapExitSurveyDtoToEntity(String studentId, StudentExitSurveyDTO dto) {

        List<StudentExitSurveyDTO.FeedbackItem> feedback = dto.getFeedback();

        String studentName = "Unknown";
        String studentEmail = "unknown@example.com";
        String branch = "Unknown";

        try {
            Optional<Student> student = studentRepository.findByStudentId(studentId);
            if (student.isPresent()) {
                studentName = student.get().getName();
                studentEmail = student.get().getEmail();
                branch = student.get().getBranch();
            }
        } catch (Exception e) {
            log.warn(" Could not fetch student details: {}", e.getMessage());
        }

        return StudentExitSurvey.builder()
                .studentId(studentId)
                .studentName(studentName)
                .studentEmail(studentEmail)
                .branch(branch)
                .yearOfPassing(java.time.Year.now().getValue())
                .q1CurriculumQuality(feedback.size() > 0 ? feedback.get(0).getOption() : null)
                .q2TeachingQuality(feedback.size() > 1 ? feedback.get(1).getOption() : null)
                .q3SyllabusUpdated(feedback.size() > 2 ? feedback.get(2).getOption() : null)
                .q4FacultyMentorship(feedback.size() > 3 ? feedback.get(3).getOption() : null)
                .q5ExamFairness(feedback.size() > 4 ? feedback.get(4).getOption() : null)
                .q6LabLibraryQuality(feedback.size() > 5 ? feedback.get(5).getOption() : null)
                .q7CampusInfrastructure(feedback.size() > 6 ? feedback.get(6).getOption() : null)
                .q8WifiItSupport(feedback.size() > 7 ? feedback.get(7).getOption() : null)
                .q9HostelQuality(feedback.size() > 8 ? feedback.get(8).getOption() : null)
                .q10CanteenQuality(feedback.size() > 9 ? feedback.get(9).getOption() : null)
                .q11PlacementEffectiveness(feedback.size() > 10 ? feedback.get(10).getOption() : null)
                .q12CareerCounseling(feedback.size() > 11 ? feedback.get(11).getOption() : null)
                .q13WorkshopsSeminars(feedback.size() > 12 ? feedback.get(12).getOption() : null)
                .q14InternshipSupport(feedback.size() > 13 ? feedback.get(13).getOption() : null)
                .q15AlumniNetwork(feedback.size() > 14 ? feedback.get(14).getOption() : null)
                .q16SportsActivities(feedback.size() > 15 ? feedback.get(15).getOption() : null)
                .q17AdminHelpfulness(feedback.size() > 16 ? feedback.get(16).getOption() : null)
                .q18GrievanceSystem(feedback.size() > 17 ? feedback.get(17).getOption() : null)
                .q19MentalHealthSupport(feedback.size() > 18 ? feedback.get(18).getOption() : null)
                .q20PersonalGrowth(feedback.size() > 19 ? feedback.get(19).getOption() : null)
                .q21CampusSafety(feedback.size() > 20 ? feedback.get(20).getOption() : null)
                .q22WouldRecommend(feedback.size() > 21 ? feedback.get(21).getOption() : null)
                .overallRating(dto.getOverallRating())
                .additionalComments(dto.getAdditionalComments())
                .build();
    }

    public Map<String, Object> checkExitSurveyStatus(String studentId) {

        boolean completed = exitSurveyRepository.existsByStudentId(studentId);

        Map<String, Object> response = new HashMap<>();
        response.put("completed", completed);
        response.put("message", completed ? "Survey already completed" : "Survey not yet completed");

        return response;
    }


    public List<AnalyticsDTO.FacultyOverview> getAllFacultyOverview() {

        List<AnalyticsDTO.FacultyOverview> overview = new ArrayList<>();
        List<Long> facultyIds = teacherFeedbackRepository.findDistinctFacultyIds();

        for (Long facultyId : facultyIds) {
            try {
                Double avgRating = teacherFeedbackRepository.getAverageRatingForFaculty(facultyId);
                Long count = (long) teacherFeedbackRepository.findByFacultyId(facultyId).size();

                String facultyName = "Unknown";
                Optional<NewTeacher> teacher = teacherRepository.findById(Math.toIntExact(facultyId));
                if (teacher.isPresent()) {
                    facultyName = teacher.get().getName();
                }

                overview.add(AnalyticsDTO.FacultyOverview.builder()
                        .facultyId(facultyId)
                        .facultyName(facultyName)
                        .overallRating(avgRating != null ? Math.round(avgRating * 100.0) / 100.0 : 0.0)
                        .totalFeedbacks(count)
                        .build());

            } catch (Exception e) {
                log.error("DEBUG: Error processing faculty {}: {}", facultyId, e.getMessage());
            }
        }

        return overview;
    }

    public AnalyticsDTO.FacultyDetailedStats getFacultyDetailedStats(Long facultyId) {

        List<TeacherFeedback> feedbacks = teacherFeedbackRepository.findByFacultyId(facultyId);

        if (feedbacks.isEmpty()) {
            return AnalyticsDTO.FacultyDetailedStats.builder()
                    .totalResponses(0L)
                    .build();
        }

        double avgClarity = feedbacks.stream().mapToInt(TeacherFeedback::getRatingClarity).average().orElse(0);
        double avgPunctuality = feedbacks.stream().mapToInt(TeacherFeedback::getRatingPunctuality).average().orElse(0);
        double avgHelpfulness = feedbacks.stream().mapToInt(TeacherFeedback::getRatingHelpfulness).average().orElse(0);
        double avgFairness = feedbacks.stream().mapToInt(TeacherFeedback::getRatingFairness).average().orElse(0);
        double avgPreparation = feedbacks.stream().mapToInt(TeacherFeedback::getRatingPreparation).average().orElse(0);
        double avgCommand = feedbacks.stream().mapToInt(TeacherFeedback::getRatingCommand).average().orElse(0);
        double avgPace = feedbacks.stream().mapToInt(TeacherFeedback::getRatingPace).average().orElse(0);
        double avgTeachingAids = feedbacks.stream().mapToInt(TeacherFeedback::getRatingTeachingAids).average().orElse(0);
        double avgEngagement = feedbacks.stream().mapToInt(TeacherFeedback::getRatingEngagement).average().orElse(0);
        double avgInteraction = feedbacks.stream().mapToInt(TeacherFeedback::getRatingInteraction).average().orElse(0);
        double avgSyllabusCoverage = feedbacks.stream().mapToInt(TeacherFeedback::getRatingSyllabusCoverage).average().orElse(0);
        double avgEvaluation = feedbacks.stream().mapToInt(TeacherFeedback::getRatingEvaluation).average().orElse(0);
        double avgAssignments = feedbacks.stream().mapToInt(TeacherFeedback::getRatingAssignments).average().orElse(0);
        double avgDoubtHandling = feedbacks.stream().mapToInt(TeacherFeedback::getRatingDoubtHandling).average().orElse(0);
        double avgAvailability = feedbacks.stream().mapToInt(TeacherFeedback::getRatingAvailability).average().orElse(0);
        double avgProfessionalism = feedbacks.stream().mapToInt(TeacherFeedback::getRatingProfessionalism).average().orElse(0);

        double overallAvg = (avgClarity + avgPunctuality + avgHelpfulness + avgFairness + avgPreparation + avgCommand +
                avgPace + avgTeachingAids + avgEngagement + avgInteraction + avgSyllabusCoverage +
                avgEvaluation + avgAssignments + avgDoubtHandling + avgAvailability + avgProfessionalism) / 16.0;


        return AnalyticsDTO.FacultyDetailedStats.builder()
                .averageClarity(round(avgClarity))
                .averagePunctuality(round(avgPunctuality))
                .averageHelpfulness(round(avgHelpfulness))
                .averageFairness(round(avgFairness))
                .averagePreparation(round(avgPreparation))
                .averageCommand(round(avgCommand))
                .averagePace(round(avgPace))
                .averageTeachingAids(round(avgTeachingAids))
                .averageEngagement(round(avgEngagement))
                .averageInteraction(round(avgInteraction))
                .averageSyllabusCoverage(round(avgSyllabusCoverage))
                .averageEvaluation(round(avgEvaluation))
                .averageAssignments(round(avgAssignments))
                .averageDoubtHandling(round(avgDoubtHandling))
                .averageAvailability(round(avgAvailability))
                .averageProfessionalism(round(avgProfessionalism))
                .overallAverage(round(overallAvg))
                .totalResponses((long) feedbacks.size())
                .build();
    }

    public Page<AnalyticsDTO.FacultyComment> getFacultyComments(Long facultyId, int page, int limit) {

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<TeacherFeedback> feedbackPage = teacherFeedbackRepository.findByFacultyIdAndCommentsTextIsNotNull(facultyId, pageable);

        Page<AnalyticsDTO.FacultyComment> comments = feedbackPage.map(f -> AnalyticsDTO.FacultyComment.builder()
                .date(f.getCreatedAt())
                .course(f.getSubjectName() != null ? f.getSubjectName() : f.getCourseId())
                .comment(f.getCommentsText())
                .semester(f.getSemester())
                .branch(f.getBranch())
                .build());

        return comments;
    }


    public AnalyticsDTO.ExitSurveyStats getExitSurveyStats() {

        long totalResponses = exitSurveyRepository.count();
        Double avgRating = exitSurveyRepository.getAverageOverallRating();

        Long promoters = exitSurveyRepository.countPromoters();
        Long passive = exitSurveyRepository.countPassive();
        Long detractors = exitSurveyRepository.countDetractors();

        List<StudentExitSurvey> allSurveys = exitSurveyRepository.findAll();

        Map<String, Integer> optionToScore = Map.of(
                "Excellent", 5,
                "Good", 4,
                "Average", 3,
                "Fair", 2,
                "Poor", 1
        );

        double academicsAvg = allSurveys.stream()
                .mapToInt(s -> (optionToScore.getOrDefault(s.getQ1CurriculumQuality(), 0) +
                        optionToScore.getOrDefault(s.getQ2TeachingQuality(), 0) +
                        optionToScore.getOrDefault(s.getQ3SyllabusUpdated(), 0) +
                        optionToScore.getOrDefault(s.getQ4FacultyMentorship(), 0) +
                        optionToScore.getOrDefault(s.getQ5ExamFairness(), 0)) / 5)
                .average().orElse(0);

        double infrastructureAvg = allSurveys.stream()
                .mapToInt(s -> (optionToScore.getOrDefault(s.getQ6LabLibraryQuality(), 0) +
                        optionToScore.getOrDefault(s.getQ7CampusInfrastructure(), 0) +
                        optionToScore.getOrDefault(s.getQ8WifiItSupport(), 0) +
                        optionToScore.getOrDefault(s.getQ9HostelQuality(), 0) +
                        optionToScore.getOrDefault(s.getQ10CanteenQuality(), 0)) / 5)
                .average().orElse(0);

        double placementsAvg = allSurveys.stream()
                .mapToInt(s -> (optionToScore.getOrDefault(s.getQ11PlacementEffectiveness(), 0) +
                        optionToScore.getOrDefault(s.getQ12CareerCounseling(), 0) +
                        optionToScore.getOrDefault(s.getQ13WorkshopsSeminars(), 0) +
                        optionToScore.getOrDefault(s.getQ14InternshipSupport(), 0) +
                        optionToScore.getOrDefault(s.getQ15AlumniNetwork(), 0)) / 5)
                .average().orElse(0);

        double studentLifeAvg = allSurveys.stream()
                .mapToInt(s -> (optionToScore.getOrDefault(s.getQ16SportsActivities(), 0) +
                        optionToScore.getOrDefault(s.getQ17AdminHelpfulness(), 0) +
                        optionToScore.getOrDefault(s.getQ18GrievanceSystem(), 0) +
                        optionToScore.getOrDefault(s.getQ19MentalHealthSupport(), 0) +
                        optionToScore.getOrDefault(s.getQ20PersonalGrowth(), 0) +
                        optionToScore.getOrDefault(s.getQ21CampusSafety(), 0) +
                        optionToScore.getOrDefault(s.getQ22WouldRecommend(), 0)) / 7)
                .average().orElse(0);


        List<AnalyticsDTO.QuestionBreakdown> questionBreakdown = generateQuestionBreakdown(allSurveys);


        return AnalyticsDTO.ExitSurveyStats.builder()
                .totalResponses(totalResponses)
                .overallRecommendationScore(avgRating != null ? round(avgRating) : 0.0)
                .categoryRatings(AnalyticsDTO.CategoryRatings.builder()
                        .academics(round(academicsAvg))
                        .infrastructure(round(infrastructureAvg))
                        .placements(round(placementsAvg))
                        .studentLife(round(studentLifeAvg))
                        .build())
                .recommendationBreakdown(AnalyticsDTO.RecommendationBreakdown.builder()
                        .promoters(promoters)
                        .passive(passive)
                        .detractors(detractors)
                        .build())
                .questionBreakdown(questionBreakdown)
                .build();
    }

    private List<AnalyticsDTO.QuestionBreakdown> generateQuestionBreakdown(List<StudentExitSurvey> surveys) {


        String[] questions = {
                "1. Quality of curriculum and its relevance to industry trends.",
                "2. Overall quality of teaching and faculty expertise.",
                "3. How updated was the syllabus with new technologies/concepts?",
                "4. Faculty's willingness to provide mentorship and support outside class.",
                "5. Fairness and transparency in the examination and grading system.",
                "6. Availability and quality of lab, library, and workshop facilities.",
                "7. Quality of campus infrastructure (classrooms, canteens, common areas).",
                "8. Reliability and speed of campus Wi-Fi and IT support.",
                "9. Quality and hygiene of hostel facilities (if applicable).",
                "10. Quality and variety of food in the campus canteen/mess.",
                "11. Effectiveness of the Training & Placement (T&P) cell.",
                "12. Quality of career counseling and guidance provided.",
                "13. Usefulness of workshops, guest lectures, and seminars conducted.",
                "14. Support for internships and industry projects.",
                "15. Strength and usefulness of the college's alumni network.",
                "16. Support for sports and extracurricular activities (e.g., clubs, fests).",
                "17. Efficiency and helpfulness of the college administration and staff.",
                "18. Effectiveness of the grievance redressal / complaint system.",
                "19. Focus on student well-being and mental health support.",
                "20. How well did the college contribute to your personal and professional growth?",
                "21. Overall campus safety and security.",
                "22. Would you recommend this college to a friend or family member?"
        };

        List<AnalyticsDTO.QuestionBreakdown> breakdown = new ArrayList<>();

        breakdown.add(createQuestionBreakdown(questions[0], surveys, StudentExitSurvey::getQ1CurriculumQuality));
        breakdown.add(createQuestionBreakdown(questions[1], surveys, StudentExitSurvey::getQ2TeachingQuality));
        breakdown.add(createQuestionBreakdown(questions[2], surveys, StudentExitSurvey::getQ3SyllabusUpdated));
        breakdown.add(createQuestionBreakdown(questions[3], surveys, StudentExitSurvey::getQ4FacultyMentorship));
        breakdown.add(createQuestionBreakdown(questions[4], surveys, StudentExitSurvey::getQ5ExamFairness));
        breakdown.add(createQuestionBreakdown(questions[5], surveys, StudentExitSurvey::getQ6LabLibraryQuality));
        breakdown.add(createQuestionBreakdown(questions[6], surveys, StudentExitSurvey::getQ7CampusInfrastructure));
        breakdown.add(createQuestionBreakdown(questions[7], surveys, StudentExitSurvey::getQ8WifiItSupport));
        breakdown.add(createQuestionBreakdown(questions[8], surveys, StudentExitSurvey::getQ9HostelQuality));
        breakdown.add(createQuestionBreakdown(questions[9], surveys, StudentExitSurvey::getQ10CanteenQuality));
        breakdown.add(createQuestionBreakdown(questions[10], surveys, StudentExitSurvey::getQ11PlacementEffectiveness));
        breakdown.add(createQuestionBreakdown(questions[11], surveys, StudentExitSurvey::getQ12CareerCounseling));
        breakdown.add(createQuestionBreakdown(questions[12], surveys, StudentExitSurvey::getQ13WorkshopsSeminars));
        breakdown.add(createQuestionBreakdown(questions[13], surveys, StudentExitSurvey::getQ14InternshipSupport));
        breakdown.add(createQuestionBreakdown(questions[14], surveys, StudentExitSurvey::getQ15AlumniNetwork));
        breakdown.add(createQuestionBreakdown(questions[15], surveys, StudentExitSurvey::getQ16SportsActivities));
        breakdown.add(createQuestionBreakdown(questions[16], surveys, StudentExitSurvey::getQ17AdminHelpfulness));
        breakdown.add(createQuestionBreakdown(questions[17], surveys, StudentExitSurvey::getQ18GrievanceSystem));
        breakdown.add(createQuestionBreakdown(questions[18], surveys, StudentExitSurvey::getQ19MentalHealthSupport));
        breakdown.add(createQuestionBreakdown(questions[19], surveys, StudentExitSurvey::getQ20PersonalGrowth));
        breakdown.add(createQuestionBreakdown(questions[20], surveys, StudentExitSurvey::getQ21CampusSafety));
        breakdown.add(createQuestionBreakdown(questions[21], surveys, StudentExitSurvey::getQ22WouldRecommend));

        return breakdown;
    }


    private AnalyticsDTO.QuestionBreakdown createQuestionBreakdown(
            String question,
            List<StudentExitSurvey> surveys,
            Function<StudentExitSurvey, String> getter) {

        long excellentCount = surveys.stream()
                .filter(s -> "Excellent".equalsIgnoreCase(getter.apply(s)))
                .count();

        long goodCount = surveys.stream()
                .filter(s -> "Good".equalsIgnoreCase(getter.apply(s)))
                .count();

        long averageCount = surveys.stream()
                .filter(s -> "Average".equalsIgnoreCase(getter.apply(s)))
                .count();

        long fairCount = surveys.stream()
                .filter(s -> "Fair".equalsIgnoreCase(getter.apply(s)))
                .count();

        long poorCount = surveys.stream()
                .filter(s -> "Poor".equalsIgnoreCase(getter.apply(s)))
                .count();

        return AnalyticsDTO.QuestionBreakdown.builder()
                .question(question)
                .excellent(excellentCount)
                .good(goodCount)
                .average(averageCount)
                .fair(fairCount)
                .poor(poorCount)
                .build();
    }

    public Page<AnalyticsDTO.ExitSurveyComment> getExitSurveyComments(int page, int limit) {
        log.info("🔵 DEBUG: Fetching exit survey comments, page={}, limit={}", page, limit);

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<StudentExitSurvey> surveyPage = exitSurveyRepository.findAll(pageable);

        Page<AnalyticsDTO.ExitSurveyComment> comments = surveyPage.map(s -> AnalyticsDTO.ExitSurveyComment.builder()
                .date(s.getCreatedAt())
                .likedMost(extractLikedMost(s.getAdditionalComments()))
                .needsImprovement(extractNeedsImprovement(s.getAdditionalComments()))
                .branch(s.getBranch())
                .yearOfPassing(s.getYearOfPassing())
                .build());


        return comments;
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    private String extractLikedMost(String comments) {
        if (comments == null) return "";
        return comments.length() > 200 ? comments.substring(0, 200) + "..." : comments;
    }

    private String extractNeedsImprovement(String comments) {
        return extractLikedMost(comments);
    }
}
