package edu.js.project.utility;

import edu.js.project.NewEntities.NewComplain;
import edu.js.project.NewEntities.NewMaterial;
import edu.js.project.NewEntities.NewSubject;
import edu.js.project.NewEntities.NewTeacher;
import edu.js.project.dto.AdminClgDto;
import edu.js.project.dto.BranchDto;
import edu.js.project.dto.ComplainDto;
import edu.js.project.dto.MaterialDto;
import edu.js.project.dto.NewComplainDto;
import edu.js.project.dto.NewMaterialDto;
import edu.js.project.dto.NewTeacherDto;
import edu.js.project.dto.SemesterDto;
import edu.js.project.dto.StudentDto;
import edu.js.project.dto.SubjectDto;
import edu.js.project.dto.TeacherDto;
import edu.js.project.dto.UnitDto;
import edu.js.project.dto.UsersDto;
import edu.js.project.entity.AdminClg;
import edu.js.project.entity.Branch;
import edu.js.project.entity.Complain;
import edu.js.project.entity.Material;
import edu.js.project.entity.Semester;
import edu.js.project.entity.Student;
import edu.js.project.entity.Subject;
import edu.js.project.entity.Teacher;
import edu.js.project.entity.Unit;
import edu.js.project.entity.Users;
import edu.js.project.enums.ComplainStatus;
import edu.js.project.enums.ComplainType;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-09T08:38:09+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.3 (Eclipse Adoptium)"
)
@Component
public class MapperImpl implements Mapper {

    @Override
    public UsersDto usersToUsersDto(Users users) {
        if ( users == null ) {
            return null;
        }

        UsersDto.UsersDtoBuilder usersDto = UsersDto.builder();

        usersDto.newTeacherDto( newTeacherToNewTeacherDto( users.getNewTeacher() ) );
        usersDto.teacherDto( teacherToTeacherDto( users.getTeacher() ) );
        usersDto.studentDto( studentToStudentDto( users.getStudent() ) );
        usersDto.adminClgDto( adminClgToAdminClgDto( users.getAdminClg() ) );
        usersDto.id( users.getId() );
        usersDto.email( users.getEmail() );
        usersDto.password( users.getPassword() );
        usersDto.roles( users.getRoles() );

        return usersDto.build();
    }

    @Override
    public AdminClgDto adminClgToAdminClgDto(AdminClg adminClg) {
        if ( adminClg == null ) {
            return null;
        }

        AdminClgDto.AdminClgDtoBuilder adminClgDto = AdminClgDto.builder();

        adminClgDto.id( adminClg.getId() );
        adminClgDto.adminId( adminClg.getAdminId() );
        adminClgDto.email( adminClg.getEmail() );
        adminClgDto.address( adminClg.getAddress() );
        adminClgDto.phone( adminClg.getPhone() );
        byte[] imageData = adminClg.getImageData();
        if ( imageData != null ) {
            adminClgDto.imageData( Arrays.copyOf( imageData, imageData.length ) );
        }

        return adminClgDto.build();
    }

    @Override
    public TeacherDto teacherToTeacherDto(Teacher teacher) {
        if ( teacher == null ) {
            return null;
        }

        TeacherDto.TeacherDtoBuilder teacherDto = TeacherDto.builder();

        teacherDto.id( teacher.getId() );
        teacherDto.name( teacher.getName() );
        teacherDto.email( teacher.getEmail() );
        teacherDto.phone( teacher.getPhone() );
        teacherDto.facultyId( teacher.getFacultyId() );
        teacherDto.designation( teacher.getDesignation() );
        teacherDto.subject( teacher.getSubject() );
        teacherDto.password( teacher.getPassword() );
        teacherDto.address( teacher.getAddress() );
        byte[] imageData = teacher.getImageData();
        if ( imageData != null ) {
            teacherDto.imageData( Arrays.copyOf( imageData, imageData.length ) );
        }

        return teacherDto.build();
    }

    @Override
    public StudentDto studentToStudentDto(Student student) {
        if ( student == null ) {
            return null;
        }

        StudentDto.StudentDtoBuilder studentDto = StudentDto.builder();

        studentDto.id( student.getId() );
        studentDto.name( student.getName() );
        studentDto.email( student.getEmail() );
        studentDto.phone( student.getPhone() );
        studentDto.studentId( student.getStudentId() );
        studentDto.password( student.getPassword() );
        studentDto.address( student.getAddress() );
        studentDto.branch( student.getBranch() );
        studentDto.year( student.getYear() );
        studentDto.regulation( student.getRegulation() );
        byte[] imageData = student.getImageData();
        if ( imageData != null ) {
            studentDto.imageData( Arrays.copyOf( imageData, imageData.length ) );
        }
        studentDto.semester( student.getSemester() );

        return studentDto.build();
    }

    @Override
    public NewTeacherDto newTeacherToNewTeacherDto(NewTeacher newTeacher) {
        if ( newTeacher == null ) {
            return null;
        }

        NewTeacherDto.NewTeacherDtoBuilder newTeacherDto = NewTeacherDto.builder();

        newTeacherDto.name( newTeacher.getName() );
        newTeacherDto.email( newTeacher.getEmail() );
        newTeacherDto.phone( newTeacher.getPhone() );
        newTeacherDto.facultyId( newTeacher.getFacultyId() );
        newTeacherDto.designation( newTeacher.getDesignation() );
        newTeacherDto.password( newTeacher.getPassword() );
        newTeacherDto.address( newTeacher.getAddress() );
        byte[] imageData = newTeacher.getImageData();
        if ( imageData != null ) {
            newTeacherDto.imageData( Arrays.copyOf( imageData, imageData.length ) );
        }
        Set<NewSubject> set = newTeacher.getSubjects();
        if ( set != null ) {
            newTeacherDto.subjects( new LinkedHashSet<NewSubject>( set ) );
        }

        return newTeacherDto.build();
    }

    @Override
    public Users usersDtoToUsers(UsersDto usersDto) {
        if ( usersDto == null ) {
            return null;
        }

        Users users = new Users();

        users.setTeacher( teacherDtoToTeacher( usersDto.getTeacherDto() ) );
        users.setStudent( studentDtoToStudent( usersDto.getStudentDto() ) );
        users.setAdminClg( adminClgDtoToAdminClg( usersDto.getAdminClgDto() ) );
        users.setId( usersDto.getId() );
        users.setEmail( usersDto.getEmail() );
        users.setPassword( usersDto.getPassword() );
        users.setRoles( usersDto.getRoles() );

        return users;
    }

    @Override
    public AdminClg adminClgDtoToAdminClg(AdminClgDto adminClgDto) {
        if ( adminClgDto == null ) {
            return null;
        }

        AdminClg.AdminClgBuilder adminClg = AdminClg.builder();

        adminClg.adminId( adminClgDto.getAdminId() );
        adminClg.email( adminClgDto.getEmail() );
        adminClg.address( adminClgDto.getAddress() );
        adminClg.phone( adminClgDto.getPhone() );
        byte[] imageData = adminClgDto.getImageData();
        if ( imageData != null ) {
            adminClg.imageData( Arrays.copyOf( imageData, imageData.length ) );
        }

        return adminClg.build();
    }

    @Override
    public Teacher teacherDtoToTeacher(TeacherDto teacherDto) {
        if ( teacherDto == null ) {
            return null;
        }

        Teacher teacher = new Teacher();

        teacher.setId( teacherDto.getId() );
        teacher.setName( teacherDto.getName() );
        teacher.setEmail( teacherDto.getEmail() );
        teacher.setPhone( teacherDto.getPhone() );
        teacher.setFacultyId( teacherDto.getFacultyId() );
        teacher.setDesignation( teacherDto.getDesignation() );
        teacher.setSubject( teacherDto.getSubject() );
        teacher.setPassword( teacherDto.getPassword() );
        teacher.setAddress( teacherDto.getAddress() );
        byte[] imageData = teacherDto.getImageData();
        if ( imageData != null ) {
            teacher.setImageData( Arrays.copyOf( imageData, imageData.length ) );
        }

        return teacher;
    }

    @Override
    public Student studentDtoToStudent(StudentDto student) {
        if ( student == null ) {
            return null;
        }

        Student.StudentBuilder student1 = Student.builder();

        student1.name( student.getName() );
        student1.email( student.getEmail() );
        student1.phone( student.getPhone() );
        student1.studentId( student.getStudentId() );
        student1.password( student.getPassword() );
        student1.address( student.getAddress() );
        student1.branch( student.getBranch() );
        student1.year( student.getYear() );
        student1.regulation( student.getRegulation() );
        byte[] imageData = student.getImageData();
        if ( imageData != null ) {
            student1.imageData( Arrays.copyOf( imageData, imageData.length ) );
        }
        student1.semester( student.getSemester() );

        return student1.build();
    }

    @Override
    public Material materialDtoToMaterial(MaterialDto materialDto) {
        if ( materialDto == null ) {
            return null;
        }

        Material.MaterialBuilder material = Material.builder();

        material.id( materialDto.getId() );
        material.title( materialDto.getTitle() );
        material.type( materialDto.getType() );
        material.url( materialDto.getUrl() );
        material.unitCode( materialDto.getUnitCode() );

        return material.build();
    }

    @Override
    public MaterialDto materialToMaterialDto(Material material) {
        if ( material == null ) {
            return null;
        }

        MaterialDto.MaterialDtoBuilder materialDto = MaterialDto.builder();

        materialDto.unitDto( unitToUnitDto( material.getUnit() ) );
        materialDto.teacherDto( teacherToTeacherDto( material.getTeacher() ) );
        materialDto.subjectDto( subjectToSubjectDto( material.getSubject() ) );
        materialDto.id( material.getId() );
        materialDto.title( material.getTitle() );
        materialDto.type( material.getType() );
        materialDto.url( material.getUrl() );
        materialDto.unitCode( material.getUnitCode() );

        return materialDto.build();
    }

    @Override
    public SemesterDto semesterToSemesterDto(Semester semester) {
        if ( semester == null ) {
            return null;
        }

        SemesterDto.SemesterDtoBuilder semesterDto = SemesterDto.builder();

        semesterDto.subjectDto( subjectSetToSubjectDtoSet( semester.getSubjects() ) );
        semesterDto.branchDto( branchToBranchDto( semester.getBranch() ) );
        semesterDto.id( semester.getId() );
        semesterDto.semesterNumber( semester.getSemesterNumber() );

        return semesterDto.build();
    }

    @Override
    public SubjectDto subjectToSubjectDto(Subject subject) {
        if ( subject == null ) {
            return null;
        }

        SubjectDto.SubjectDtoBuilder subjectDto = SubjectDto.builder();

        subjectDto.id( subject.getId() );
        subjectDto.name( subject.getName() );
        subjectDto.subjectCode( subject.getSubjectCode() );

        return subjectDto.build();
    }

    @Override
    public ComplainDto complainToComplainDto(Complain complain) {
        if ( complain == null ) {
            return null;
        }

        ComplainDto.ComplainDtoBuilder complainDto = ComplainDto.builder();

        complainDto.id( complain.getId() );
        complainDto.type( complain.getType() );
        complainDto.comment( complain.getComment() );
        if ( complain.getMaterialId() != null ) {
            complainDto.materialId( complain.getMaterialId() );
        }

        return complainDto.build();
    }

    @Override
    public Complain complainDtoToComplain(ComplainDto complainDto) {
        if ( complainDto == null ) {
            return null;
        }

        Complain.ComplainBuilder complain = Complain.builder();

        complain.id( complainDto.getId() );
        complain.type( complainDto.getType() );
        complain.comment( complainDto.getComment() );
        complain.materialId( complainDto.getMaterialId() );

        return complain.build();
    }

    @Override
    public NewMaterialDto newMaterialToNewMaterialDto(NewMaterial newMaterial) {
        if ( newMaterial == null ) {
            return null;
        }

        NewMaterialDto.NewMaterialDtoBuilder newMaterialDto = NewMaterialDto.builder();

        newMaterialDto.id( newMaterial.getId() );
        newMaterialDto.pdfFilename( newMaterial.getPdfFilename() );
        newMaterialDto.materialId( newMaterial.getMaterialId() );
        newMaterialDto.subjectCode( newMaterial.getSubjectCode() );
        newMaterialDto.facultyId( newMaterial.getFacultyId() );
        newMaterialDto.materialType( newMaterial.getMaterialType() );
        newMaterialDto.regulationId( newMaterial.getRegulationId() );

        return newMaterialDto.build();
    }

    @Override
    public NewComplain newComplainDtoToNewComplain(NewComplainDto newComplainDto) {
        if ( newComplainDto == null ) {
            return null;
        }

        NewComplain.NewComplainBuilder newComplain = NewComplain.builder();

        newComplain.id( newComplainDto.getId() );
        newComplain.comment( newComplainDto.getComment() );
        newComplain.studentId( newComplainDto.getStudentId() );
        newComplain.materialId( newComplainDto.getMaterialId() );
        newComplain.facultyId( newComplainDto.getFacultyId() );
        if ( newComplainDto.getComplainType() != null ) {
            newComplain.complainType( Enum.valueOf( ComplainType.class, newComplainDto.getComplainType() ) );
        }
        if ( newComplainDto.getComplainStatus() != null ) {
            newComplain.complainStatus( Enum.valueOf( ComplainStatus.class, newComplainDto.getComplainStatus() ) );
        }

        return newComplain.build();
    }

    @Override
    public NewComplainDto newComplainToNewComplainDto(NewComplain newComplain) {
        if ( newComplain == null ) {
            return null;
        }

        NewComplainDto.NewComplainDtoBuilder newComplainDto = NewComplainDto.builder();

        newComplainDto.id( newComplain.getId() );
        newComplainDto.comment( newComplain.getComment() );
        newComplainDto.studentId( newComplain.getStudentId() );
        newComplainDto.materialId( newComplain.getMaterialId() );
        newComplainDto.facultyId( newComplain.getFacultyId() );
        if ( newComplain.getComplainType() != null ) {
            newComplainDto.complainType( newComplain.getComplainType().name() );
        }
        if ( newComplain.getComplainStatus() != null ) {
            newComplainDto.complainStatus( newComplain.getComplainStatus().name() );
        }

        return newComplainDto.build();
    }

    protected UnitDto unitToUnitDto(Unit unit) {
        if ( unit == null ) {
            return null;
        }

        UnitDto.UnitDtoBuilder unitDto = UnitDto.builder();

        unitDto.id( unit.getId() );
        unitDto.unitCode( unit.getUnitCode() );
        unitDto.unitNumber( unit.getUnitNumber() );
        unitDto.title( unit.getTitle() );
        unitDto.subjectCode( unit.getSubjectCode() );

        return unitDto.build();
    }

    protected Set<SubjectDto> subjectSetToSubjectDtoSet(Set<Subject> set) {
        if ( set == null ) {
            return null;
        }

        Set<SubjectDto> set1 = LinkedHashSet.newLinkedHashSet( set.size() );
        for ( Subject subject : set ) {
            set1.add( subjectToSubjectDto( subject ) );
        }

        return set1;
    }

    protected BranchDto branchToBranchDto(Branch branch) {
        if ( branch == null ) {
            return null;
        }

        BranchDto.BranchDtoBuilder branchDto = BranchDto.builder();

        branchDto.id( branch.getId() );
        branchDto.name( branch.getName() );

        return branchDto.build();
    }
}
