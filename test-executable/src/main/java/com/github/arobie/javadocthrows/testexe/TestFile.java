package com.github.arobie.javadocthrows.testexe;

public class TestFile {

    public void noException() {}

    public void exception() {
        throw new RuntimeException();
    }

    /**
     * @throws RuntimeException description
     */
    public void exceptionUnqualifiedDoc() {
        throw new RuntimeException();
    }

    /**
     * @throws java.lang.RuntimeException description
     */
    public void exceptionFullyQualifiedDoc() {
        throw new RuntimeException();
    }

    /**
     * @throws java.lang.RuntimeException
     */
    public void exceptionNoDescription() {
        throw new RuntimeException();
    }


    public void catchException() {
        try {
            exception();
        } catch(RuntimeException e) {
            System.out.println("Got error");
        }
    }

    public void conditionalThrow(boolean shouldThrow) {
        if(shouldThrow) {
            throw new RuntimeException();
        }
    }

    /**
     * @throws java.lang.RuntimeException description
     */
    public void conditionalThrowWithDoc(boolean shouldThrow) {
        if(shouldThrow) {
            throw new RuntimeException();
        }
    }

    /**
     * @throws java.lang.RuntimeException description
     */
    public void declaresNotThrown() {}

    public static class CustomException extends RuntimeException {}

    public void customException() {
        throw new CustomException();
    }

    /**
     * @throws com.github.arobie.javadocthrows.testexe.TestFile.CustomException description
     */
    public void customExceptionDoc() {
        throw new CustomException();
    }

    /**
     * @throws com.github.arobie.javadocthrows.testexe.TestFile.CustomException description
     */
    public void declaresCustomException() {}

    //should hit NullPointerException when s is null, but gets out of memory error
    public boolean stateSpaceExplosion(String s, char c) {
        for(char sc : s.toCharArray()) {
            if(c == sc) {
                return true;
            }
        }
        return false;
    }

}
