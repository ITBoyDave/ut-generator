package cn.bigboydave.ut.generotor.data;

/**
 * @author bigboydave
 * @description
 * @email 15839622863@163.com
 * @date 8/18/19 3:32 PM
 * @srcFile CommandArgs.java
 */
public class CommandArgs {
    /**
     * SERVICE
     * DAO
     * CONTROLLER
     */
    private String template;
    /**
     * 类全名
     */
    private String clazzName;

    /**
     * 生层类的包前缀
     */
    private String prefixPackage;

    /**
     * dao mapperScan package0,package1,package2
     */
    private String basePackages;

    /**
     * 作者
     */
    private String author = "BigBoyDave";

    /**
     * 描述
     */
    private String description = "";

    /**
     * 邮箱
     */
    private String email = "15839622863@163.com|wb-lc434842@cainiao.com";

    /**
     * date日期格式
     */
    private String dateFormatPattern = "yyyy-MM-dd HH:mm";

    /**
     * 输出文件path
     */
    private String outputFilePath;

    /**
     * 是否写出文件到输出位置
     */
    private boolean write = true;

    /**
     *
     */
    private Boolean innerMode = false;

    /**
     * 配置类
     */
    private String configClazz;

    /**
     * configClazzImport
     */
    private String configClazzImport;

    private boolean print = true;

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    public String getPrefixPackage() {
        return prefixPackage;
    }

    public void setPrefixPackage(String prefixPackage) {
        this.prefixPackage = prefixPackage;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBasePackages() {
        return basePackages;
    }

    public void setBasePackages(String basePackages) {
        this.basePackages = basePackages;
    }

    public String getDateFormatPattern() {
        return dateFormatPattern;
    }

    public void setDateFormatPattern(String dateFormatPattern) {
        this.dateFormatPattern = dateFormatPattern;
    }

    public String getOutputFilePath() {
        return outputFilePath;
    }

    public void setOutputFilePath(String outputFilePath) {
        this.outputFilePath = outputFilePath;
    }

    public boolean isWrite() {
        return write;
    }

    public void setWrite(boolean write) {
        this.write = write;
    }

    public Boolean getInnerMode() {
        return innerMode;
    }

    public void setInnerMode(Boolean innerMode) {
        this.innerMode = innerMode;
    }

    public String getConfigClazz() {
        return configClazz;
    }

    public void setConfigClazz(String configClazz) {
        this.configClazz = configClazz;
    }

    public String getConfigClazzImport() {
        return configClazzImport;
    }

    public void setConfigClazzImport(String configClazzImport) {
        this.configClazzImport = configClazzImport;
    }

    public boolean isPrint() {
        return print;
    }

    public void setPrint(boolean print) {
        this.print = print;
    }
}
