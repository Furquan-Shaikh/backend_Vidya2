package edu.js.project.service.impl;

import edu.js.project.NewEntities.NewTeacher;
import edu.js.project.dto.*;
import edu.js.project.entity.*;
import edu.js.project.repository.*;
import edu.js.project.service.UserService;
import edu.js.project.utility.Mapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final AdminRepository adminRepository;
    private final TeacherRepository teacherRepository;
    private final RegulationRepository regulationRepository;
    private final BranchRepository branchRepository;
    private final SemesterRepository semesterRepository;
    private final SubjectRepository subjectRepository;
    private final UnitRepository unitRepository;
    private final MaterialRepository materialRepository;
    private final NewTeacherRepo newTeacherRepo;
    private final NewSubjectRepo newSubjectRepo;
    private final PasswordEncoder encode;
    private final NewsRepository newsRepository;

    private final Mapper mapper;

    @Override
    @Transactional
    public void addUser(TeacherDto teacherDto) {


        TeacherDto.TeacherDtoBuilder teacher = TeacherDto.builder();
        TeacherDto facultyDto = teacher.name(teacherDto.getName())
                .facultyId(teacherDto.getFacultyId())
                .email(teacherDto.getEmail())
                .password(encode.encode(teacherDto.getPassword()))
                .build();

        UsersDto facultyUser = new UsersDto();
        facultyUser.setEmail(facultyDto.getEmail());
        facultyUser.setPassword(facultyDto.getPassword());
        facultyUser.setRoles("Faculty");

        Users users = mapper.usersDtoToUsers(facultyUser);
        Users saveFaculty = userRepository.save(users);

        Teacher faculty = mapper.teacherDtoToTeacher(facultyDto);
        faculty.setUsers(saveFaculty);
        teacherRepository.save(faculty);
        saveFaculty.setTeacher(faculty);
//        userRepository.save(saveFaculty);




    }

    @Override
    @Transactional()
    public void addUser(StudentDto studentDto) {


        UsersDto.UsersDtoBuilder userDto = UsersDto.builder();
        UsersDto userWithoutStudent = userDto.roles("Student")
                .email(studentDto.getEmail())
                .password(studentDto.getPassword())
                .build();

        Users user = mapper.usersDtoToUsers(userWithoutStudent);
        Users savedUser = userRepository.save(user);

        Student student = mapper.studentDtoToStudent(studentDto);
        student.setUsers(savedUser);
        Student savedStudent = studentRepository.save(student);

        savedUser.setStudent(savedStudent);



    }



    @Transactional
    @Modifying
    @Override
    public void addAdmin() {

        AdminClg xyzTown = AdminClg.builder().phone("9897654123")
                .address("xyz town")
                .email("admin@gmail.com")
                .adminId("123486")
                .build();
        if(adminRepository.findByAdminId(xyzTown.getAdminId()).isEmpty()) {
            System.out.println("Inside save owner");
            UsersDto.UsersDtoBuilder userDto = UsersDto.builder();
            UsersDto userWithoutAdmin = userDto.roles("Admin")
                    .email(xyzTown.getEmail())
                    .password(encode.encode("Pass@123"))
                    .build();

            Users user = mapper.usersDtoToUsers(userWithoutAdmin);
            Users savedUser = userRepository.save(user);

            xyzTown.setUsers(savedUser);
            AdminClg savedAdmin = adminRepository.save(xyzTown);

            savedUser.setAdminClg(savedAdmin);


        }
    }

    @Override
    @Transactional
    public void removeUser(long userId) {
        Users user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User Not found"));
        userRepository.delete(user);
    }


    @Transactional(readOnly = true)
    @Override
    public boolean verifyUser(String email, String password) {


        UsersDto userData = userRepository.findByEmail(email).map(
                data -> {

                    if (data.getRoles().equals("admin"))
                        Hibernate.initialize(data.getAdminClg());
                    else if (data.getRoles().equals("student"))
                        Hibernate.initialize(data.getStudent());
                    else
                        Hibernate.initialize(data.getTeacher());

                    return mapper.usersToUsersDto(data);

                }
        ).orElseThrow(
                () -> new RuntimeException("user not found")
        );


        return userData.getPassword().equals(password);

    }

    @Transactional
    @Override
    public List<UserTableDto> getFacultyList() {


//        List<Teacher> all = teacherRepository.findAll();
//        return all.stream().map(mapper::teacherToTeacherDto).toList();
        return userRepository.getFacultyList().stream().map(faculty -> {

            UserTableDto dto = new UserTableDto();
            dto.setFacultyId(faculty.getNewTeacher().getFacultyId());
            dto.setName(faculty.getNewTeacher().getName());
            dto.setEmail(faculty.getEmail());
            dto.setDesignation(faculty.getNewTeacher().getDesignation());
            dto.setAddress(faculty.getNewTeacher().getAddress());
            return dto;

        }).toList();
    }

    @Override
    public List<StudentDto> getStudentList() {
//        return studentRepository.findAll().stream().map(mapper::studentToStudentDto).toList();
        return userRepository.getStudentList().stream().map(mapper::usersToUsersDto).map(UsersDto::getStudentDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public <T extends Base> T getUserDetail(long userId) {

        Users users = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found")
        );

        try{

            if(Objects.equals(users.getRoles(),"admin")){

                return (T) users.getAdminClg();

            } else if (Objects.equals(users.getRoles(),"Faculty")) {

                T teacher = (T) users.getTeacher();
                Teacher t = (Teacher) teacher;

                return teacher;

            }else {
                return (T) users.getStudent();
            }

        }catch (RuntimeException e){


            Base b = new Base();
            return (T) b;

        }


    }

    @Override
    @Transactional()
    public void addRegulation(RegulationInputDto input) {


        Regulation r = new Regulation();
        r.setName(input.getRegulation());
        Regulation save = regulationRepository.save(r);
        Branch branch = addBranch(save, input);
        r.getBranches().add(branch);


    }

    public Branch addBranch(Regulation reg, RegulationInputDto input){

        Branch b = new Branch();
        b.setName(input.getBranch());
        b.setRegulation(reg);
        branchRepository.save(b);
        Semester semester = addSemester(b, input);
        b.getSemesters().add(semester);
        return b;

    }

    public Semester addSemester(Branch branch, RegulationInputDto input){


        Semester s = new Semester();
        s.setSemesterNumber(input.getSemester());
        s.setBranch(branch);
        Semester sem =semesterRepository.save(s);
        Set<Subject> subjectSet = new HashSet<>();
        input.getSubjectDto().forEach(
                sub->{
                    Subject subject = addSubject(sem, sub.getName(), sub.getSubjectCode());
                    subjectSet.add(subject);
                }
        );
        sem.setSubjects(subjectSet);
        return sem;


    }

    public Subject addSubject(Semester sem, String subjectName, String subCode){

            Subject s = new Subject();
            s.setSemesters(sem);
            s.setName(subjectName);
            s.setSubjectCode(subCode);
            subjectRepository.save(s);
            return s;

    }

    @Transactional
    public void addUnit(UnitDto dto){

        Unit unit = new Unit();
        unit.setUnitNumber(dto.getUnitNumber());
        unit.setTitle(dto.getTitle());
        Subject sub = subjectRepository.findBySubjectCode(dto.getSubjectCode()).orElseThrow(
                ()-> new RuntimeException("Wrong Subject code")
        );
        unit.setSubject(sub);
        unit.setUnitCode(dto.getUnitCode());
        unit.setSubjectCode(dto.getSubjectCode());
        Unit save = unitRepository.save(unit);



    }

    @Transactional
    public void addMaterial(MaterialDto dto, String subCode, String facultyId, String unitCode){


        //subject fetch using subjectCode
        //unit add using unitId
        //teacher add using facultyId

        Material material = mapper.materialDtoToMaterial(dto);
        Teacher teacher = teacherRepository.findByFacultyId(facultyId).orElseThrow(
                () -> new RuntimeException("User Not Found")
        );
        Subject sub = subjectRepository.findBySubjectCode(subCode).orElseThrow(
                () -> new RuntimeException("Subject Not Available")
        );
        Unit unit = unitRepository.findByUnitCode(unitCode).orElseThrow(
                () -> new RuntimeException("Unit not found")
        );
        material.setTeacher(teacher);
        material.setUnit(unit);
        material.setSubject(sub);
        Material save = materialRepository.save(material);
//        addUnitInMaterial(unit,material);

    }


    public void addUnitInMaterial(Unit unit, Material material){
        unit.getMaterials().add(material);

    }

//    @Transactional
//    public void updateStudentDetail(StudentDto studentDto, MultipartFile photo){
//
//        Student student = studentRepository.findByStudentId(studentDto.getStudentId()).orElseThrow(
//                () -> new RuntimeException("Wrong Student Id")
//        );
//
//        Student std = mapper.studentDtoToStudent(studentDto);
//        student.setAddress(std.getAddress());
//        student.setSemester(std.getSemester());
//        student.setPhone(std.getPhone());
//        student.setRegulation(std.getRegulation());
//
//
//    }


    @Transactional
    public void updateStudentDetail(StudentDto studentDto, MultipartFile photo) throws IOException, IOException {
        Student student = studentRepository.findByStudentId(studentDto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Wrong Student Id"));

        // update simple fields
        student.setAddress(studentDto.getAddress());
        student.setSemester(studentDto.getSemester());
        student.setPhone(studentDto.getPhone());
        student.setRegulation(studentDto.getRegulation());

        // handle photo if present
        if (photo != null && !photo.isEmpty()) {
            // optional: validate content-type and size
            String contentType = photo.getContentType();
            if (contentType == null || (!contentType.startsWith("image/"))) {
                throw new RuntimeException("Uploaded file is not an image");
            }
            // optional: size check e.g., 5MB max
            long maxBytes = 5L * 1024 * 1024;
            if (photo.getSize() > maxBytes) {
                throw new RuntimeException("File too large");
            }

            byte[] bytes = photo.getBytes(); // read bytes
            student.setImageData(bytes);     // ensure Student entity has imageData byte[] with @Lob
            // optional: store MIME type
            // optionally store original filename: student.setImageName(photo.getOriginalFilename());
        }

        studentRepository.save(student); // flush changes
    }


    @Transactional
    public void updateFacultyDetail(NewTeacherDto teacherDto, MultipartFile photo) throws IOException {


        NewTeacher faculty = newTeacherRepo.findByFacultyId(teacherDto.getFacultyId()).orElseThrow(
                () -> new RuntimeException("Wrong facultyId")
        );


        faculty.setAddress(teacherDto.getAddress());
        faculty.setSubjects(newSubjectRepo.findBySubjectCodeIn(teacherDto.getSubjectCodes()));
        faculty.setPhone(teacherDto.getPhone());
        faculty.setDesignation(teacherDto.getDesignation());

        if (photo != null && !photo.isEmpty()) {
            // optional: validate content-type and size
            String contentType = photo.getContentType();
            if (contentType == null || (!contentType.startsWith("image/"))) {
                throw new RuntimeException("Uploaded file is not an image");
            }
            // optional: size check e.g., 5MB max
            long maxBytes = 5L * 1024 * 1024;
            if (photo.getSize() > maxBytes) {
                throw new RuntimeException("File too large");
            }

            byte[] bytes = photo.getBytes(); // read bytes
            faculty.setImageData(bytes);     // ensure Student entity has imageData byte[] with @Lob
            // optional: store MIME type
            // optionally store original filename: student.setImageName(photo.getOriginalFilename());
        }


    }

    @Transactional
    public boolean addUserToDB(StudentDto studentDto){


        Student studentData = mapper.studentDtoToStudent(studentDto);
        boolean empty = studentRepository.findByStudentId(studentData.getStudentId()).isEmpty();
        if(empty) {
            UsersDto.UsersDtoBuilder userDto = UsersDto.builder();

            UsersDto userWithoutStudent = userDto.roles("Student")
                    .email(studentDto.getEmail())
                    .password(encode.encode(studentDto.getPassword()))
                    .build();

            Users user = mapper.usersDtoToUsers(userWithoutStudent);
            Users savedUser = userRepository.save(user);

            Student student = mapper.studentDtoToStudent(studentDto);
            student.setPassword(userWithoutStudent.getPassword());
            student.setUsers(savedUser);
            Student savedStudent = studentRepository.save(student);

            savedUser.setStudent(savedStudent);

            return true;
        }else
            return false;

    }

    @Override
    public UsersDto getUserDetail(String email) {

        return userRepository.findByEmail(email).map(mapper::usersToUsersDto).orElseThrow(
                () -> new RuntimeException("Login credential not found")
        );

    }

    @Override
    public boolean isUserExists(String email) {
        // Check if user exists by email
        // This assumes you have a method in your repository
        return userRepository.existsByEmail(email);
        // Or if checking by username:
        // return userRepository.existsByUsername(email);
    }

    @Transactional
    @Override
    public boolean resetPassword(String email, String newPassword) {
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        String encoded = encode.encode(newPassword);
        user.setPassword(encoded);
        userRepository.save(user);

        // Also keep copies in related entities (student/teacher/admin) in sync if you store password there
        if (user.getStudent() != null) {
            Student s = user.getStudent();
            s.setPassword(encoded);
            studentRepository.save(s);
        }
        if (user.getTeacher() != null) {
            Teacher t = user.getTeacher();
            // if Teacher entity has password field, update it - if not, ignore
            try {
                t.setPassword(encoded);
                teacherRepository.save(t);
            } catch (Exception ignored) {}
        }
//        if (user.getAdminClg() != null) {
//            AdminClg a = user.getAdminClg();
//            try {
//                a.setPassword(encoded);
//                adminRepository.save(a);
//            } catch (Exception ignored) {}
//        }

        return true;
    }

    @Override
    public List<NewsDto> getNewList() {
        return newsRepository.findAll().stream().map(news -> {
            NewsDto newsDto = mapper.newsToNewsDto(news);
            newsDto.setFacultyName(news.getTeacher().getName());
            return newsDto;
        }).toList();
    }


}
