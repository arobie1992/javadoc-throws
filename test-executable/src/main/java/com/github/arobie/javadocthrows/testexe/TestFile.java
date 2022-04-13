package com.github.arobie.javadocthrows.testexe;

public class TestFile {

    /**
     * This is a test javadoc
     */
    public void noExceptionsNoDoc() {}

    public void catchEx() {
        try {
            throwEx();
        } catch(RuntimeException e) {
            System.out.println("Got error");
            e.printStackTrace(System.out);
        }
    }

    public void throwEx() {
        throw new RuntimeException();
    }

    public void throwsExceptionNoDoc(boolean shouldThrow) {
        if(shouldThrow) {
            throw new RuntimeException();
        }
    }

    public boolean unsafeEquals(Boolean b1, boolean b2) {
        return eq(b1, b2);
    }

    public boolean equals(Boolean b1, boolean b2) {
        if(b1 != null) {
            return eq(b1, b2);
        } else {
            return false;
        }
    }

    private boolean eq(boolean b1, boolean b2) {
        return b1 == b2;
    }

    public static class CustomException extends RuntimeException {}

    /**
     * @throws CustomException
     */
    public void throwCustomException() {
        throw new CustomException();
    }

}
