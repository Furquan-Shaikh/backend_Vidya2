package edu.js.project.utility;

import edu.js.project.NewEntities.NewComplain;
import edu.js.project.NewEntities.NewMaterial;
import edu.js.project.NewEntities.NewTeacher;
import edu.js.project.dto.*;
import edu.js.project.entity.*;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@org.mapstruct.Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface Mapper {

    @Mapping(target = "newTeacherDto", source = "newTeacher")
    @Mapping(target = "teacherDto", source = "teacher")
    @Mapping(target = "studentDto", source = "student")
    @Mapping(target = "adminClgDto", source = "adminClg")

    UsersDto usersToUsersDto(Users users);

    AdminClgDto adminClgToAdminClgDto(AdminClg adminClg);

    TeacherDto teacherToTeacherDto(Teacher teacher);

    StudentDto studentToStudentDto(Student student);

    @Mapping(target = "subjectCodes", ignore = true)
    @Mapping(target = "materials", ignore = true)
    NewTeacherDto newTeacherToNewTeacherDto(NewTeacher newTeacher);


    @Mapping(target = "teacher", source = "teacherDto")
    @Mapping(target = "student", source = "studentDto")
    @Mapping(target = "adminClg", source = "adminClgDto")
    Users usersDtoToUsers(UsersDto usersDto);

    AdminClg adminClgDtoToAdminClg(AdminClgDto adminClgDto);

    @Mapping(target = "users", ignore = true)
    @Mapping(target = "materials", ignore = true)
    Teacher teacherDtoToTeacher(TeacherDto teacherDto);

    @Mapping(target = "users", ignore = true)
    Student studentDtoToStudent(StudentDto student);


    @Mapping(target = "unit", ignore = true)
    @Mapping(target = "teacher", ignore = true)
    Material materialDtoToMaterial(MaterialDto materialDto);

    @Mapping(target = "unitDto", source = "unit")
    @Mapping(target = "teacherDto", source = "teacher")
    @Mapping(target = "subjectDto", source = "subject")
    MaterialDto materialToMaterialDto(Material material);

    @Mapping(target = "subjectDto", source = "subjects")
    @Mapping(target = "branchDto", source = "branch")
    SemesterDto semesterToSemesterDto(Semester semester);

    @Mapping(target = "unitDto", ignore = true)
    @Mapping(target = "semestersDto", ignore = true)
    SubjectDto subjectToSubjectDto(Subject subject);

    ComplainDto complainToComplainDto(Complain complain);


    Complain complainDtoToComplain(ComplainDto complainDto);


    NewMaterialDto newMaterialToNewMaterialDto(NewMaterial newMaterial);

    NewComplain newComplainDtoToNewComplain(NewComplainDto newComplainDto);
    NewComplainDto newComplainToNewComplainDto(NewComplain newComplain);

}
