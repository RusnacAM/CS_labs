# Web Authentication and Authorization

### Course: Cryptography and Security
### Author: Rusnac Ana-Maria

### Objectives
1. Take all the work from previous laboratory works and put them in a web service.
2. Web service should have basic authentication and 2FA.
3. The web service needs to simulate user authorization.
4. The web service needs to provide services such as classical ciphers.

### Server
<div style="text-align: justify">&nbsp;&nbsp;&nbsp;&nbsp;The web server itself was realised with the com.sun.net.httpserver java 
package. The package provides basic connectivity, the way it work is you
first create an HTTPServer instance, and bind it to a port:</div>
    
    HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

And for the endpoints, first the context is created, and assigned to
a handler:

    HttpContext hc1 = server.createContext("/", new Login());

<div style="text-align: justify">&nbsp;&nbsp;&nbsp;&nbsp;The handler for different endpoints just receives the http request,
sets a response, an appropriate status code, and processes the request.
Not much else is needed to make the server work, only these steps
are necessary for a functioning server in java. In this laboratory,
all services are accessed through GET requests since the user is just trying
to access some cipher.</div>

### Basic Authentication
<div style="text-align: justify">&nbsp;&nbsp;&nbsp;&nbsp;The package described in the previous section also provides some
basic authentication, just through username and password. The Basic
Authenticator class is used, which is actually a subclass of the Authenticator
class. It is an abstract class and must be extended to provide 
an implementation of checkCredentials which is 
called to verify each incoming request:</div>

    public cipherAuthentication(String realm) {
            super(realm);
        }

        @Override
        public boolean checkCredentials(String username, String password){
            return db.checkPassword(username, password);
        }

The realm here is a security policy domain defined for a 
web or application server.

    private static final BasicAuthenticator basicAuthenticator = new cipherAuthentication("Lab3");

<div style="text-align: justify">&nbsp;&nbsp;&nbsp;&nbsp;If the checkCredentials method return true, then the user is authenticated.
The method to check the credentials is in the Database class, which has
changed from the previous laboratory work. Now the database stores the
users as objects User, instead of a simple key-value pair, where the User object
has the attributes:</div>

    String username;
    String password;
    List<String> authorizedCiphers;

The last attribute is described in the Authorization section.

<div style="text-align: justify">&nbsp;&nbsp;&nbsp;&nbsp;The Database has been accordingly modified to function with this change,
and the checkPassword method returns a boolean value accordingly if the
hash of the password matches the hash of the given password or not.</div>

    StringBuffer hashedPassword = new StringBuffer();
        try {
            hashedPassword = hashPassword(password);
        } catch (Exception e) {
            System.out.println("Incorrect password and/or username..");
        }
        return userMap.get(username).password.equals(hashedPassword.toString());

<div style="text-align: justify">&nbsp;&nbsp;&nbsp;&nbsp;The user can log in by submitting a request to the /login endpoint, where
they input their name and password. Upon a successful login, they receive a response
that they have been logged in. For the purposes of this laboratory, Postman
was used to send requests:</div>

![](https://cdn.discordapp.com/attachments/758662311287980075/1052971135320473662/Screen_Shot_2022-12-15_at_17.31.17.png)

![](../../../../../../../../../var/folders/r1/zsw4cxkj1rz62wnyb4sbrhyh0000gn/T/TemporaryItems/NSIRD_screencaptureui_mRnNnJ/Screen Shot 2022-12-15 at 16.36.50.png)

<div style="text-align: justify">&nbsp;&nbsp;&nbsp;&nbsp;Note that this successful login page shows up only after inputting the 2fa code as well,
but without the 2fa it works the same way of course, and you only need a username and
a password. You can't access any other endpoints aka cipher services if you
are not logged in, no matter if the user has authorization to that service or not.</div>

### 2FA
<div style="text-align: justify">&nbsp;&nbsp;&nbsp;&nbsp;The two factor authentication is done using goog authenticator, and all
related method are contained in the Authentication class. For this laboratory work,
2fa is done with an additional 6 digit code provided by the google authenticator
application. For the code to be generated, the user need a secret key, genrated by the
generateSecretKey method:</div>

    SecureRandom random = new SecureRandom();
    byte[] bytes = new byte[20];
    random.nextBytes(bytes);
    Base32 base32 = new Base32();
    return base32.encodeToString(bytes);

Once the user has the secret key, they input it into the google auth
app, and a new random code will be generated every 30 seconds.

![](https://miro.medium.com/max/4800/1*XP0PyjKEYgLd7kTpajw89Q.webp)

Since this is a pretty simple application working with postman, the user
inputs this code in the url parameters

    Map<String, String> params = WebServer.utils.WebqueryToMap(t.getRequestURI().getQuery());
    String twoFACode = params.get("2fa");

The WebqueryToMap method is a method from Utils, and just takes this
code from the parameters:

    for (String param : query.split("&")) {
        String[] pair = param.split("=");
            if (pair.length > 1) {
                result.put(pair[0], pair[1]);
            } else {
                result.put(pair[0], "");
            }
        }
    return result;

<div style="text-align: justify">&nbsp;&nbsp;&nbsp;&nbsp;Then at the endpoint it checks if this code matches the one generated
by the getTOTPCode method, and cheanges the state of logged in to true so
the user can't acces any other end point without logging in and using 2 factor
authentication:</div>

![](../../../../../../../../../var/folders/r1/zsw4cxkj1rz62wnyb4sbrhyh0000gn/T/TemporaryItems/NSIRD_screencaptureui_3cE2yI/Screen Shot 2022-12-15 at 16.58.46.png)

If the code itself is wrong or has expired:

![](../../../../../../../Desktop/Screen Shot 2022-12-15 at 16.36.40.png)

### Authorization
<div style="text-align: justify">&nbsp;&nbsp;&nbsp;&nbsp;Lastly, authorization allows different users to access different services/ciphers. The 
authorization parameters is a list of authorized ciphers/services saved as a list
for each user in the User object. Based on the user name, the app checks
if the user is authorized to specific service and either allows or denies access.
The method to check this is again in the database:</div>

    return userMap.get(username).authorizedCiphers.contains(cipher);

For example, the user "amy" is authorized to access the caesar, caesarPerm, and 
vigenere services, and for these services they get an appropriate response:

![](../../../../../../../../../var/folders/r1/zsw4cxkj1rz62wnyb4sbrhyh0000gn/T/TemporaryItems/NSIRD_screencaptureui_TFwrpA/Screen Shot 2022-12-15 at 17.04.02.png)

But if they try to acces the playfair service:

![](../../../../../../../../../var/folders/r1/zsw4cxkj1rz62wnyb4sbrhyh0000gn/T/TemporaryItems/NSIRD_screencaptureui_NMRIOA/Screen Shot 2022-12-15 at 17.04.46.png)

And of course, it applies for all other users. For example user1 has access
to only the playfair cipher, so:

![](../../../../../../../../../var/folders/r1/zsw4cxkj1rz62wnyb4sbrhyh0000gn/T/TemporaryItems/NSIRD_screencaptureui_YBI1uC/Screen Shot 2022-12-15 at 17.06.29.png)

And if they try to access any other service, they get the same error message:

![](../../../../../../../../../var/folders/r1/zsw4cxkj1rz62wnyb4sbrhyh0000gn/T/TemporaryItems/NSIRD_screencaptureui_2Y3HqF/Screen Shot 2022-12-15 at 17.07.06.png)

### Conclusions

<div style="text-align: justify">&nbsp;&nbsp;&nbsp;&nbsp;This laboratory work explored authorization and authentication in java, as well
as multi factor authentication. Through this laboratory work, it provided experience of
setting up a simple http server in java through httpServer package, and how
to perform basic authentication using the same package. Besides the basic authentication,
it provided the chance to work with google authenticator and understand how two factor
or multi factor authentication works, and how to implement it. Finally, it explored
different ways in which authorization can be done, in order to block certain sensitive information
from other users, or to just give different levels of access.</div>