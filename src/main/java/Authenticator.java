public interface Authenticator {

    Authenticator update(byte[] bytes);

    byte[] digest();

    Authenticator reset();

}
