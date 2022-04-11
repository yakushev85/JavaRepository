package org.oiakushev.hwjbackendwizard;

import org.oiakushev.hwjbackendwizard.model.Entity;
import org.oiakushev.hwjbackendwizard.model.Field;
import org.oiakushev.hwjbackendwizard.model.FieldType;
import org.oiakushev.hwjbackendwizard.model.ProjectMetaInfo;
import org.oiakushev.hwjbackendwizard.util.PasswordUtil;
import org.oiakushev.hwjbackendwizard.util.StringNamingUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class DataController {
    private final ArrayList<Entity> entities;
    private final ArrayList<FieldType> fieldTypes;
    private final HashMap<String, String> maskMap;

    public DataController() {
        entities = new ArrayList<>();
        entities.add(Entity.generateUserEntity());
        fieldTypes = new ArrayList<>(FieldType.NOT_LINKED_FIELD_TYPES_LIST);
        maskMap = new HashMap<>();
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public ArrayList<FieldType> getFieldTypes() {
        ArrayList<FieldType> allFieldTypes = new ArrayList<>(fieldTypes);

        for (Entity itemEntity : entities) {
            FieldType linkedFieldType = new FieldType(FieldType.FieldTypeValue.idLink);
            linkedFieldType.setLinkedEntity(itemEntity);
            allFieldTypes.add(linkedFieldType);
        }

        return allFieldTypes;
    }

    public Entity addNewEntityWithName(String newEntityName) {
        Entity newEntity = new Entity(newEntityName);
        ArrayList<Field> newFields = new ArrayList<>();
        newFields.add(Field.generateIdField());
        newEntity.setFields(newFields);
        entities.add(newEntity);

        return newEntity;
    }

    public void addNewFieldWithName(Entity currentEntity, String fieldNewName) {
        for (Entity entity : entities) {
            if (entity.equals(currentEntity)) {
                Field newField = new Field(fieldNewName, new FieldType(FieldType.FieldTypeValue.vString));

                entity.getFields().add(newField);
            }
        }
    }

    public void generateCode(String pathTo, ProjectMetaInfo projectMetaInfo) throws IOException {
        String packageName = projectMetaInfo.getGroupId() + "." + projectMetaInfo.getArtifactId().toLowerCase();
        maskMap.put("packageName", packageName);
        maskMap.put("artifactId", projectMetaInfo.getArtifactId());
        maskMap.put("groupId", projectMetaInfo.getGroupId());
        maskMap.put("version", projectMetaInfo.getVersion());
        maskMap.put("token1", PasswordUtil.generatePassword(20));
        maskMap.put("password", PasswordUtil.generatePassword(20));

        String packagePath = packageName.replaceAll("\\.", "/");
        String mainPath = pathTo + "/" + projectMetaInfo.getArtifactId();
        String devRootPath = mainPath + "/src/main/java/" + packagePath;
        String resourcePath = mainPath + "/src/main/resources";
        String testPath = mainPath + "/src/test/java";

        String devBeanPath = devRootPath + "/bean";
        String devModelPath = devRootPath + "/model";
        String devRepositoryPath = devRootPath + "/repository";
        String devServicePath = devRootPath + "/service";
        String devControllerPath = devRootPath + "/controller";
        String devSecurityPath = devRootPath + "/security";

        File mainPathFile = new File(mainPath);
        File devRootPathFile = new File(devRootPath);
        File resourcePathFile = new File(resourcePath);
        File testPathFile = new File(testPath);

        File devBeanPathFile = new File(devBeanPath);
        File devModelPathFile = new File(devModelPath);
        File devRepositoryPathFile = new File(devRepositoryPath);
        File devServicePathFile = new File(devServicePath);
        File devControllerPathFile = new File(devControllerPath);
        File devSecurityPathFile = new File(devSecurityPath);

        mainPathFile.mkdirs();
        devRootPathFile.mkdirs();
        resourcePathFile.mkdirs();
        testPathFile.mkdirs();

        devBeanPathFile.mkdirs();
        devModelPathFile.mkdirs();
        devRepositoryPathFile.mkdirs();
        devServicePathFile.mkdirs();
        devControllerPathFile.mkdirs();
        devSecurityPathFile.mkdirs();

        // db scripts
        generateDbScript(mainPathFile);

        // main pom and resources
        generateFileFromResources("main/test_client_py", "testClient.py", mainPathFile);
        generateFileFromResources("main/pom_xml", "pom.xml", mainPathFile);
        generateFileFromResources("res/application_properties", "application.properties", resourcePathFile);

        // bean
        generateFileFromResources("bean/AuthRequest_java", "AuthRequest.java", devBeanPathFile);

        // root
        generateFileFromResources("AppApplication_java", "AppApplication.java", devRootPathFile);
        generateFileFromResources("GlobalExceptionHandler_java", "GlobalExceptionHandler.java", devRootPathFile);
        generateFileFromResources("ServletInitializer_java", "ServletInitializer.java", devRootPathFile);
        generateFileFromResources("SpringSecurityConfig_java", "SpringSecurityConfig.java", devRootPathFile);

        // security
        generateFileFromResources("security/JwtCsrfFilter_java", "JwtCsrfFilter.java", devSecurityPathFile);
        generateFileFromResources("security/JwtTokenRepository_java", "JwtTokenRepository.java", devSecurityPathFile);
        generateFileFromResources("security/SecurityHelper_java", "SecurityHelper.java", devSecurityPathFile);

        // controller
        generateFileFromResources("controller/AuthController_java", "AuthController.java", devControllerPathFile);

        // model
        generateModelFiles(devModelPathFile);

        // repository
        generalRepositoryFiles(devRepositoryPathFile);

        // service
        generateServiceFiles(devServicePathFile);

        // controller
        generateControllerFiles(devControllerPathFile);
    }

    private void generateDbScript(File mainPathFile) throws IOException {
        String appName = maskMap.get("artifactId");

        FileWriter fileWriter =
                new FileWriter(new File(mainPathFile, "dbSchema.sql"));

        fileWriter.write("CREATE DATABASE " + appName + ";\n" +
                "CREATE USER '" + appName + "'@'localhost' IDENTIFIED BY '" + maskMap.get("password") + "';\n" +
                "GRANT ALL PRIVILEGES ON *.* TO '" + appName + "'@'localhost';\n\nUSE " + appName + ";\n\n");

        for (Entity entity : entities) {
            fileWriter.write("CREATE TABLE `" + entity.getName() + "` (\n");
            for (Field field : entity.getFields()) {
                fileWriter.write("`" + field.getName() + "` " + field.getDbType() + ",\n");
            }

            fileWriter.write("\tPRIMARY KEY (`id`)");

            for (Field field : entity.getFields()) {
                if (field.getType().getFieldType() == FieldType.FieldTypeValue.idLink &&
                        field.getType().getLinkedEntity() != null) {
                    Entity linkedEntity = field.getType().getLinkedEntity();

                    fileWriter.write(",\n" +
                            "KEY `" + entity.getName() + "_" + field.getName() + "_fkey_idx` " +
                            "(`" + field.getName() + "`),\n" +
                            "CONSTRAINT `" + entity.getName() + "_" + field.getName() + "_fkey` " +
                            "FOREIGN KEY (`" + field.getName() + "`) " +
                            "REFERENCES `" + linkedEntity.getName() + "` " +
                            "(`id`) ON DELETE NO ACTION ON UPDATE NO ACTION");
                }
            }

            fileWriter.write("\n) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;\n\n");
        }

        fileWriter.close();
    }

    private void generateControllerFiles(File devControllerPathFile) throws IOException {
        for (Entity entity : entities) {
            maskMap.put("entity", entity.getName());
            String lowEntityName = StringNamingUtil.toFirsLetterLower(entity.getName());
            maskMap.put("lentity", lowEntityName);
            maskMap.put("llentity", entity.getName().toLowerCase());

            StringBuilder endpointsCode = new StringBuilder();

            for (Field field : entity.getFields()) {
                String upFieldName = StringNamingUtil.toFirstLetterUpper(field.getName());

                if (field.isAddSearchBy() && field.getType().getFieldType() != FieldType.FieldTypeValue.bytes) {
                    endpointsCode
                            .append("\t@RequestMapping(value = \"/")
                            .append(field.getName().toLowerCase()).append("/{").append(field.getName())
                            .append("}\", method = RequestMethod.GET, produces = \"application/json\")\n")
                            .append("\tpublic String findBy").append(upFieldName).append("(@PathVariable ")
                            .append(field.getType().getFieldType().getOriginalType()).append(" ")
                            .append(field.getName()).append(") {\n")
                            .append("\t\treturn gson.toJson(").append(lowEntityName).append("Service.findBy").append(upFieldName)
                            .append("OrderByIdDesc(").append(field.getName()).append("));\n")
                            .append("\t}\n\n");
                }

                if (field.getType().getFieldType() == FieldType.FieldTypeValue.bytes) {
                    String dataFileCode = "\t@GetMapping(value = \"/{id}/" + field.getName() + "\", produces = MediaType.IMAGE_JPEG_VALUE)\n" +
                            "\tpublic Resource get" + upFieldName + "(@PathVariable Long id) throws Exception {\n" +
                            "\t\t" + entity.getName() + " " + lowEntityName + " = " + lowEntityName + "Service.getById(id);\n\n" +
                            "\t\tif (" + lowEntityName + " == null) {\n" +
                            "\t\t\treturn null;\n" +
                            "\t\t}\n\n" +
                            "\t\treturn new ByteArrayResource(" + lowEntityName+ ".get" +upFieldName+ "());\n" +
                            "\t}\n\n" +
                            "\t@Transactional\n" +
                            "\t@PostMapping(\"{id}/" + field.getName() + "\")\n" +
                            "\tpublic String upload" + upFieldName + "(@PathVariable Long id, @RequestParam MultipartFile dataFile) throws Exception {\n" +
                            "\t\t" + entity.getName() + " " + lowEntityName + " = " + lowEntityName + "Service.getById(id);\n\n" +
                            "\t\tif (" + lowEntityName + " == null) {\n" +
                            "\t\t\treturn null;\n" +
                            "\t\t}\n\n" +
                            "\t\t" + lowEntityName + ".setData(dataFile.getBytes());\n\n" +
                            "\t\treturn gson.toJson(" + lowEntityName + "Service.update(" + lowEntityName + "));\n" +
                            "\t}\n\n";
                    endpointsCode.append(dataFileCode);
                }
            }

            maskMap.put("endpoints", endpointsCode.toString());

            generateFileFromResources("controller/DefaultController_java", entity.getName() + "Controller.java", devControllerPathFile);
        }
    }

    private void generateServiceFiles(File devServicePathFile) throws IOException {
        for (Entity entity : entities) {
            FileWriter fileWriter =
                    new FileWriter(new File(devServicePathFile, entity.getName() + "Service.java"));

            String preCode = "package " + maskMap.get("packageName") + ".service;\n\n" +
                    "import " + maskMap.get("packageName") + ".model.*;\n" +
                    "import javax.servlet.http.HttpServletRequest;\n" +
                    "import java.util.List;\n" +
                    "import java.util.Date;\n\n" +
                    "public interface " + entity.getName() + "Service {\n\n" +
                    "\tList<" + entity.getName() + "> getAll();\n\n" +
                    "\t" + entity.getName() + " getById(Long id);\n\n" +
                    "\t" + entity.getName() + " add(" + entity.getName() + " value);\n\n" +
                    "\t" + entity.getName() + " update(" + entity.getName() + " value);\n\n";
            fileWriter.write(preCode);

            for (Field field : entity.getFields()) {
                if (field.isAddSearchBy()) {
                    String upName = StringNamingUtil.toFirstLetterUpper(field.getName());

                    String line = "\tList<" + entity.getName() + "> findBy" + upName + "OrderByIdDesc("
                            + field.getType().getFieldType().getOriginalType() + " " + field.getName() + ");\n\n";
                    fileWriter.write(line);
                }
            }

            if (entity.getName().equals("User")) {
                fileWriter.write("\tUser getUserFromRequest(HttpServletRequest request);\n\n");
            }

            fileWriter.write("}\n");
            fileWriter.close();

            FileWriter fileWriterImpl =
                    new FileWriter(new File(devServicePathFile, entity.getName() + "ServiceImpl.java"));

            String repositoryName = entity.getName().toLowerCase() + "Repository";
            String repositoryType = entity.getName() + "Repository";
            String preCodeImpl = "package " + maskMap.get("packageName") + ".service;\n\n" +
                    "import " + maskMap.get("packageName") + ".model.*;\n" +
                    "import " + maskMap.get("packageName") + ".repository.*;\n" +
                    "import " + maskMap.get("packageName") + ".security.JwtTokenRepository;\n" +
                    "import org.springframework.beans.factory.annotation.Autowired;\n" +
                    "import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;\n" +
                    "import org.springframework.security.web.csrf.CsrfToken;\n" +
                    "import org.springframework.stereotype.Service;\n" +
                    "import org.springframework.transaction.annotation.Transactional;\n\n" +
                    "import javax.servlet.http.HttpServletRequest;\n" +
                    "import com.google.common.collect.Lists;\n" +
                    "import java.util.ArrayList;\n" +
                    "import java.util.List;\n" +
                    "import java.util.Optional;\n" +
                    "import java.util.Date;\n\n" +
                    "@Service\n" +
                    "public class " + entity.getName() + "ServiceImpl implements " + entity.getName() + "Service {\n\n" +
                    "\t@Autowired\n" +
                    "\tprivate " + repositoryType + " " + repositoryName + ";\n\n" +
                    "\t@Autowired\n" +
                    "\tprivate JwtTokenRepository jwtTokenRepository;\n\n" +
                    "\t@Override\n" +
                    "\tpublic List<" + entity.getName() + "> getAll() {\n" +
                    "\t\treturn Lists.newArrayList(" + repositoryName + ".findAll());\n\t}\n\n" +
                    "\t@Override\n" +
                    "\tpublic " + entity.getName() + " getById(Long id) {\n" +
                    "\t\treturn " + repositoryName + ".findById(id).orElse(null);\n\t}\n\n" +
                    "\t@Override\n\t@Transactional\n" +
                    "\tpublic " + entity.getName() + " add(" + entity.getName() + " value) {\n" +
                    "\t\tvalue.setId(null);\n"+
                    "\t\treturn " + repositoryName + ".save(value);\n\t}\n\n" +
                    "\t@Override\n\t@Transactional\n" +
                    "\tpublic " + entity.getName() + " update(" + entity.getName() + " value) {\n" +
                    "\t\treturn " + repositoryName + ".save(value);\n\t}\n\n";
            fileWriterImpl.write(preCodeImpl);

            for (Field field : entity.getFields()) {
                if (field.isAddSearchBy()) {
                    String upName = StringNamingUtil.toFirstLetterUpper(field.getName());
                    String methodName = "findBy" + upName + "OrderByIdDesc";

                    String line = "\t@Override\n\tpublic List<" + entity.getName() + "> findBy" + upName + "OrderByIdDesc("
                            + field.getType().getFieldType().getOriginalType() + " " + field.getName() + ") {\n" +
                            "\t\treturn " + repositoryName + "." + methodName + "(" + field.getName() + ");\n\t}\n\n";
                    fileWriterImpl.write(line);
                }
            }

            if (entity.getName().equals("User")) {
                String implementedMethod = "\t@Override\n\tpublic User getUserFromRequest(HttpServletRequest request) {\n" +
                        "\t\tCsrfToken csrfToken = jwtTokenRepository.loadToken(request);\n" +
                        "\t\tString username = jwtTokenRepository.getUsernameFromToken(csrfToken.getToken());\n" +
                        "\t\tList<User> userList = userRepository.findByUsernameOrderByIdDesc(username);\n\n" +
                        "\t\tif (userList.isEmpty()) {\n" +
                        "\t\t\treturn null;\n" +
                        "\t\t} else {\n" +
                        "\t\t\treturn userList.get(0);\n" +
                        "\t\t}\n\t}\n\n";
                fileWriterImpl.write(implementedMethod);
            }

            fileWriterImpl.write("}\n");
            fileWriterImpl.close();
        }
    }

    private void generalRepositoryFiles(File devRepositoryPathFile) throws IOException {
        for (Entity entity : entities) {
            FileWriter fileWriter =
                    new FileWriter(new File(devRepositoryPathFile, entity.getName() + "Repository.java"));

            String preCode = "package " + maskMap.get("packageName") + ".repository;\n\n" +
                    "import " + maskMap.get("packageName") + ".model.*;\n" +
                    "import org.springframework.data.repository.CrudRepository;\n" +
                    "import org.springframework.stereotype.Repository;\n\n" +
                    "import java.util.List;\n" +
                    "import java.util.Date;\n\n" +
                    "@Repository\n" +
                    "public interface " + entity.getName() + "Repository extends CrudRepository<" +
                    entity.getName() + ", Long> {\n";
            fileWriter.write(preCode);

            for (Field field : entity.getFields()) {
                if (field.isAddSearchBy()) {
                    String upName = StringNamingUtil.toFirstLetterUpper(field.getName());

                    String line = "\tList<" + entity.getName() + "> findBy" + upName + "OrderByIdDesc("
                            + field.getType().getFieldType().getOriginalType() + " " + field.getName() + ");\n";
                    fileWriter.write(line);
                }
            }

            fileWriter.write("}\n");
            fileWriter.close();
        }
    }

    private void generateModelFiles(File devModelPathFile) throws IOException {
        for (Entity entity : entities) {
            FileWriter fileWriter = new FileWriter(new File(devModelPathFile, entity.getName() + ".java"));

            String preCode = "package " + maskMap.get("packageName") + ".model;\n\n" +
                    "import javax.persistence.*;\n" +
                    "import java.io.Serializable;\n" +
                    "import java.util.Date;\n\n" +
                    "@Entity\n" +
                    "@Table(name = \"" + entity.getName() +"\")\n" +
                    "public class " + entity.getName() + " implements Serializable {\n";
            fileWriter.write(preCode);

            for (Field field : entity.getFields()) {
                if (field.getType().getFieldType() == FieldType.FieldTypeValue.id) {
                    fileWriter.write("\t@Id\n" +
                            "\t@GeneratedValue(strategy = GenerationType.IDENTITY)\n");
                }
                if (field.getType().getFieldType() == FieldType.FieldTypeValue.bytes) {
                    fileWriter.write("\t@Lob\n");
                }
                String line = "\tprivate " + field.getType().getFieldType().getOriginalType() + " " + field.getName() + ";\n";
                fileWriter.write(line);
            }

            fileWriter.write("\n");

            for (Field field : entity.getFields()) {
                String upName = StringNamingUtil.toFirstLetterUpper(field.getName());

                String setter = "\tpublic void set" + upName + "(" + field.getType().getFieldType().getOriginalType() + " " + field.getName() + ") " +
                        "{\n\t\tthis." + field.getName() + " = " + field.getName() + ";\n\t}\n\n";
                fileWriter.write(setter);

                String getter = "\tpublic " + field.getType().getFieldType().getOriginalType() + " get" + upName + "() " +
                        "{\n\t\treturn " + field.getName() + ";\n\t}\n\n";
                fileWriter.write(getter);
            }

            fileWriter.write("}\n");
            fileWriter.close();
        }
    }

    private void generateFileFromResources(String filename, String newFileName, File pathTo) throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream(filename);

        if (inputStream == null) {
            throw new IOException("Can't do getResourceAsStream because inputStream == null");
        }

        Scanner fileScanner =  new Scanner(inputStream);
        FileWriter fileWriter = new FileWriter(new File(pathTo, newFileName));

        while (fileScanner.hasNextLine()) {
            String inLine = fileScanner.nextLine();
            fileWriter.write(StringNamingUtil.filterLine(inLine, maskMap) + "\n");
        }

        fileWriter.close();
        fileScanner.close();
    }
}
