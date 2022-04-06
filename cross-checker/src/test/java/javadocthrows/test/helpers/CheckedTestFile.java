package javadocthrows.test.helpers;

import javadocthrows.test.helpers.exception.CustomRuntimeException;

public class CheckedTestFile {

    /* Public methods */

    public void noDocPublicMethod() {}

    /**
     * Test doc comment
     * Hi
     */
    public void noThrowsPublicMethod() {}

    /**
     * @throws RuntimeException
     */
    public void throwsWithDocPublicMethod() {
        throw new RuntimeException();
    }

    /**
     * @throws RuntimeException
     */
    public void noThrowsWithDocPublicMethod() {}

    /**
     * @throws CustomRuntimeException
     */
    public void throwsCustomWithDocPublicMethod() {
        throw new CustomRuntimeException();
    }

    /* Private methods */

    private void noDocPrivateMethod() {}

    /**
     *
     */
    private void noThrowsPrivateMethod() {}

    /**
     * @throws RuntimeException
     */
    private void throwsWithDocPrivateMethod() {
        throw new RuntimeException();
    }

    /**
     * @throws RuntimeException
     */
    private void noThrowsWithDocPrivateMethod() {}

    /**
     * @throws CustomRuntimeException
     */
    private void throwsCustomWithDocPrivateMethod() {
        throw new CustomRuntimeException();
    }

    /* Protected methods */

    protected void noDocProtectedMethod() {}

    /**
     *
     */
    protected void noThrowsProtectedMethod() {}

    /**
     * @throws RuntimeException
     */
    protected void throwsWithDocProtectedMethod() {
        throw new RuntimeException();
    }

    /**
     * @throws RuntimeException
     */
    protected void noThrowsWithDocProtectedMethod() {}

    /**
     * @throws CustomRuntimeException
     */
    protected void throwsCustomWithDocProtectedMethod() {
        throw new CustomRuntimeException();
    }

    /* Package private methods */

    void noDocPkgMethod() {}

    /**
     *
     */
    void noThrowsPkgMethod() {}

    /**
     * @throws RuntimeException
     */
    void throwsWithDocPkgMethod() {
        throw new RuntimeException();
    }

    /**
     * @throws RuntimeException
     */
    void noThrowsWithDocPkgMethod() {}

    /**
     * @throws CustomRuntimeException
     */
    void throwsCustomWithDocPkgMethod() {
        throw new CustomRuntimeException();
    }

    /* misc */

    // This is a non doc comment above a method
    public void plainComment() {}

    /* This is a block comment above a method */
    public void blockComment() {}

    /**
     * Javadoc comment
     */
    //non-doc comment
    public void commentBetweenDocAndMethod() {}

    /**
     * /**
     */
    public void docWithDocStartSeq() {}

    /*
     *
     */
    public void multilineBlockComment() {}

    /**
     * Returns the input {@link String}.
     * @param s
     * @return s
     */
    public String testArgAndReturn(String s) {
        return s;
    }

}
