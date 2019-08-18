package cn.bigboydave.ut.generotor.interfaces;

/**
 * @author bigboydave
 * @description
 * @email 15839622863@163.com
 * @date 8/17/19 10:33 PM
 * @srcFile Parser.java
 */
public interface Parser<E, T> {

    /**
     * 是否支持parse
     *
     * @param element
     * @return
     */
    boolean isEffect(E element);

    /**
     * parse
     *
     * @param element
     * @return
     */
    default T parse(E element) {
        return null;
    }

    /**
     * parse
     *
     * @param element
     * @param target
     * @return
     */
    default T parse(E element, T target) {
        return target;
    }
}
