package cn.bigboydave.ut.generotor.data;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author bigboydave
 * @description
 * @email 15839622863@163.com
 * @date 8/18/19 10:59 PM
 * @srcFile TemplateData.java
 */
public class TemplateData implements Serializable {

    private String pkg;
    private Set<String> imports = new CopyOnWriteArraySet<>();
    private String signature;
    private String clazzNamespace;
    private String innerClass;
    private String field;
    private String testMethod;

    public String getPkg() {
        return pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }

    public Set<String> getImports() {
        return imports;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getClazzNamespace() {
        return clazzNamespace;
    }

    public void setClazzNamespace(String clazzNamespace) {
        this.clazzNamespace = clazzNamespace;
    }

    public String getInnerClass() {
        return innerClass;
    }

    public void setInnerClass(String innerClass) {
        this.innerClass = innerClass;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getTestMethod() {
        return testMethod;
    }

    public void setTestMethod(String testMethod) {
        this.testMethod = testMethod;
    }

    public String render() {
        StringBuilder sb = new StringBuilder(this.pkg).append("\n");
        for (String im : this.imports) {
            sb.append(im);
        }
        sb.append("\n").append(this.signature);
        if (null != this.clazzNamespace) {
            sb.append(this.clazzNamespace).append("\n");
        }
        if (null != this.innerClass) {
            sb.append(this.innerClass).append("\n");
        }
        if (null != this.field) {
            sb.append(this.field).append("\n");
        }
        if (null != this.testMethod) {
            sb.append(this.testMethod).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }

    @Override
    public String toString() {
        return "TemplateData{" +
                "pkg='" + pkg + '\'' +
                ", imports=" + imports +
                ", signature='" + signature + '\'' +
                ", clazzNamespace='" + clazzNamespace + '\'' +
                ", innerClass='" + innerClass + '\'' +
                ", field='" + field + '\'' +
                ", testMethod='" + testMethod + '\'' +
                '}';
    }
}
