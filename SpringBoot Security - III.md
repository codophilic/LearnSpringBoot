# SpringBoot Security III

## OAuth2 (Open Authorization 2.0)

### What problems does OAuth2 solves?

![alt text](image.png)

- 100% of the enterprise organizations or enterprise projects, they leverage OAuth2 to implement authentication and authorization inside their applications. Only smaller organizations or some startup organizations or some low-critical applications, they're going to ignore the OAuth2 framework.
- We all use this various Google products. Google has primarily Gmail as a product. They also have Google Maps, YouTube, Google Photo, Google Drive. So similarly, there are many other applications which are developed and maintained by Google or Alphabet. All these applications of Google, they have their own separate code base. They're going to be deployed in different, different servers. But have you anytime wondered how all these Google products, they allow the same credentials as an input? We as an end user, whenever we want to use any Google product, we need to create our own account with the help of Gmail. So the same Gmail credentials you can use to authenticate in all the remaining applications. For example, if you want to use YouTube to see the videos or to upload some videos, we can log in into these YouTube mobile application or web application with the Gmail credentials that we have created previously. The same applies for the remaining Google products.
- So how this is possible is ? behind the scenes, Google as an organization, it is following the OAuth2 standard or OAuth2 framework. So whenever someone is following the OAuth2, it has a separate authorization server. So this separate authorization server, it is responsible for storing the end user credentials, and the same Auth server is also responsible for end user authentication and authorization. So initially, when an end user register into the Gmail account, a separate Auth server will handle the registration process and will store the end user credentials. The same Auth server will be used by all the applications inside Google to authenticate the user and to perform authorization of the end user. This way, we as an end users of Google products, we don't have to create different, different accounts in different products and we don't have to maintain different credentials for different products of Google.
- So the responsibility of this Auth server is, it is going to perform the authentication of the end user and it is going to issue the tokens during the login operation. The same token can be used with the other applications as well. For example, inside one of my browser tab, think like, you have logged in into your Gmail account. With the help of Auth server, you will get a token. Now, this token will be saved somewhere inside your browser. Maybe after a few hours or maybe after few days if you try to open one of the different product of Google like Maps or YouTube or Photos or Drive, it is not going to ask you the credentials again, it is simply going to use the same token that is available inside my browser. And with this token, the authentication and authorization is going to happen without entering any credentials.
- Let's try to take one more scenario and try to understand what are the advantages that we get if you try to maintain a separate Auth server inside an organization.


![alt text](image-2.png)


- Think like, there is a bank application which has multiple websites supporting accounts, loans, and cards. If these bank application, if they're not following the OAuth2 framework, then the bank customers, they have to register and maintain different user profiles in all the three systems. So there is a Loans application, Cards application, and Accounts application. Inside each of these applications, if they have their own authentication and authorization logic, then the end user will end up creating different credentials for different applications, and all these credentials are going to be stored in different storage systems.
- So here, clearly, these people, they're not following the OAuth2 framework inside the bank organization. With these, we are going to have multiple challenges. The very first challenge or drawback is the security-related logic like authentication and authorization, it is getting duplicated in multiple places. Though all these three applications, they have the same logic, it is getting duplicated across different applications. So in future, if there is a requirement change around the authentication and authorization, the same has to be done in all these three places. So this organization has only three applications. Inside microservices environment, we'll have hundreds of microservices. So maintaining all the security-related logic in each of the microservice individually is going to be a cumbersome process.
- The next challenge is, think like, this loan application has issued a token during the login process to the client application. So the same token is not going to make any sense to the other applications. That's other app, they're going to reject that token. They always accept their own credentials, and they're going to issue their own tokens during the authentication and authorization process.
- It is recommended that always try to separate your security-related logic into an Auth server so that your individual applications, they are going to simply have the logic related to the business logic. This way, you are not clubbing the business logic with the security logic.
- OAuth2 framework tries to solve **Delegated Authentication or Authorization** issue.

![alt text](image-1.png)

- Think like you are an end user who maintain all your photos inside the Google Photos, and there is a third-party app with the name PhotoEditor. So this PhotoEditor third-party app, it is going to allow you to edit your photos with the help of AI, and it is also going to help you to create albums out of your photos. It is also going to help you to create collage of your photos.
- Since this third-party app is providing all these features, you decided to use this third-party app. So let's try to imagine this scenario without OAuth2. In a very common basic scenario, what you're going to do? You're going to download the photo from your Google Photo. The same you're going to upload into the PhotoEditor for performing some edits because the PhotoEditor has its own credentials for you login process. So this is going to be very cumbersome process.
- So to make your life easy, PhotoEditor, they supports integration with the Google Photos. The PhotoEditor third-party app, it is going to ask the end user to provide his Google Photo credentials. Though sharing the Google credentials directly to this website is a risky option. So the guys who develop this website, if they're good, what they're going to do, they're going to use the credentials of this end user, and with the help of these credentials, they're going to read the photos from the Google Photos website. Once the photos are loaded inside this PhotoEditor application, they're going to perform some editing or creating albums or creating collages. If the guys misuse your credentials, they could delete your photos and perform malicious activity using it on other products. They can perform all the actions that you can perform as an end user inside the Photos website.
- Now, let's try to imagine the same scenario with the OAuth2. So with OAuth2 what is going to happen is, the end user will not share his Google credentials to the third-party application. Instead, OAuth2 going to let Google Photos to issue an temporary access token.Using this temporary access token, the PhotoEditor app, they can only read the photos from the end user Google Photos account. With the access token that is issued by the Google Photos on behalf of the end user, it is only useful to read the images or to load the images. Apart from reading, they can't delete the photos, they can't change the email, they can't change the password. Any other operations will straight away rejected by the Google photos because the access token issued to the third-party app does not have enough privileges.
- So whenever we are using OAuth2, what is happening? The end user delegating the authentication and authorization to the PhotoEditor app to read his photos from the Google Photos. And whenever the end user feel like he no more needed the services of this website, he can easily revoke the permission that he has initially issued to this website by logging into the Google account.

### About OAuth2


![alt text](image-3.png)

- OAuth2 stands for **Open Authorization**, so whatever number you see, which is 2.0, this represents the current version of OAuth2 framework that is being used by everyone. In short, we can call this OAuth 2.O as OAuth 2.
- Before OAuth 2, there used to be a framework with the name OAuth 1, which is the very first version of OAuth. Since this OAuth 1 has lot many drawbacks, this OAuth 1 is updated with the newer version with the name OAuth 2 by addressing all the latest security standards.
- OAuth 2 is a free and open source protocol that is built by the open source community. When we say OAuth 2, it is a security standard, or it is a protocol, or it is a specification that every organization has to follow whenever they're trying to implement authentication and authorization inside their organization.
- So don't be under the assumption that OAuth is a product or it is a library. With the help of OAuth 2, the open source community, they only declared the standards or rules and regulations that everyone has to follow. By leveraging all these standards, many organizations, they build their own authorization server that follows OAuth 2 framework.
- With the help of OAuth2 framework, we can give one application permission to access your data in another application. We can authorize one application to access our data or features in another application on our behalf via tokens also called access tokens. So this can be achieved without actually giving them our password or credentials (the example of PhotoEditor scenario)
- Whenever an access token is issued to a client application or a third-party application, with the help of that access token, they should be able to read that data of the end user. So with the help of this access token, they should be able to perform only a limited functionality. So this access token is very similar to the access card that we are going to be issued at any office or hotel. So with the help of access card, we should be able to go to our particular floor or our particular desk or our particular room.

>[!IMPORTANT]
> - OAuth 2.0 stands for Open Authorization 2.0, an authorization protocol that allows a website or application to access resources on behalf of a user. It is the industry standard for online authorization. 
> - OAuth 2.0 works by using access tokens to grant temporary access to third parties. These tokens can have an expiration date and can be stored securely by clients


- The beauty of OAuth 2 framework lies inside the grant types that it is going to support.

![alt text](image-4.png)

- So based upon the type of application or based upon the scenario that your organization is into, you need to follow one of the grant types that are supported by the OAuth 2 framework. So inside the [OAuth 2](https://oauth.net/2/) specifications , there are clear guidelines explaining about which grant type flow that we need to use under which scenario.
- For example, if an end user is involved during the authentication and authorization process, then we can use either of the **Authorization Code or PKCE** grant type flow based upon the type of our application. If your application is built using the JavaScript frameworks, like, Angular, React, or mobile applications, in such scenarios, you need to use **PKCE**. Otherwise, you can safely use Authorization Code.
- The next grant type flow that we have here is **Client Credentials**. So these Client Credentials, we need to use whenever two different backend applications or two different APIs, they're trying to communicate with each other. So in these scenarios, the end user is not involved.
- Whenever two different devices or IoT applications, if they're trying to communicate with each other, we need to use the **Device Code** grant type flow. 
- Similarly, there is a **Refresh Token** grant type flow that can be used in the scenarios whenever an access token is expired and if you're looking to get a new access token.
- **Implicit flow** is very similar to the Authorization Code grant type flow, but it's a older version of Authorization Code grant type flow, which has some drawbacks. That's why this **Implicit flow** is deprecated right now. The **Password grant** type flow is also a flavor of Authorization Code grant type flow, and this is also deprecated due to its drawbacks.

### OAuth2 Terminology

![alt text](image-5.png)


- Lets see about these jargons used in OAuth2 
- In the scenario of PhotoEditor, the Resource Owner refers to the end user who owns the photos inside the Google Photos application. So in the scenario of PhotoEditor, what are the resources that everyone tried to get? The resources are images or photos. So who owns those photos? The end user. That's why we call the end user as **Resource Owner**.
- **Client** is an application which is trying to read the end users resources or the Resource Owner resources. So in the scenario of PhotoEditor, the PhotoEditor application, our website, it is going to be called as client because this is the application which is showing some interest to interact with the Google after taking enough permissions from the Resource Owner or the end user.
- **Authorization Server** so this is the server which knows about the Resource Owner. In other words, the Resource Owner should have an account inside these Authorization Server. In the scenarios of PhotoEditor, we can consider Google Auth server as the Authorization Server because it is going to have all the authorization logic. So always remember auth server which is responsible to store the end user credentials and to perform the authentication and issue the access tokens.
- **Resource Server** this is the server where the resources are going to be stored. If the client application want to read the resources or the images of the end user, the client application needs to reach out to the Resource Server. We are calling this as a Resource Server because this is the server which holds all the resources, and it is going to expose enough APIs and services to the client applications so that they can consume and get all the resources that they want. So in the scenario of PhotoEditor, the PhotoEditor application, it is going to call the `/getimages` API, which is exposed by the Google Photos resource server. So when client application asks for the resources, obviously, the resource server will expect an access token before providing the response. **So how to get the access token?, the client first has to reach out to the authorization server to get the access token. Once the access token is received, using the same access token, it can reach out to the Resource Server to fetch the resources**.
- In smaller organizations, there is a good possibility that a single server can act as both resource server and auth server. As of now, the spring boot application that we have built so far, though, we're not following any OAuth 2 standards, we clubbed all our business logic and security logic into a single application. That's why we can call our spring boot application as both auth server plus Resource Server.
- **Scopes** are the granular permissions the client wants, such as access to data or to perform certain actions. In the scenario of PhotoEditor, the auth server can issue an access token to client with the scope of only reading images. So since the scope is only read images, if the client application trying to invoke the Resource Server to delete an image, then it is going to get an 403 Error because it does not have enough privileges. So these scopes, in other words, we can call it as authorities or roles.

### OAuth2 Flow

- Lets take an example , when we login on LinkedIn we get below page.

![alt text](image-6.png)

- If you see here, we have option to sign up using email or use social login. LinkedIn website is also supporting social login. So what is a social login? Social login is a process where we can quickly sign in into a third party application by entering our social credentials from Google or Apple or Facebook, Twitter, GitHub, LinkedIn. So since all these Google, Apple, or any other social organizations, since they are very big organizations, they have their own authorization servers.
- So whenever we use social login option, what is going to happen? your profile details which are stored inside the Google auth server, they are going to be fetched and given to the LinkedIn. So LinkedIn behind the scenes, what it is going to do is, by taking your profile details from Google or Apple, it is going to create an account for your very quickly.
- So in future, whenever you want to log in into this application, you just have to use the same option, which is sign in with Google. And with that, your authentication is going to be super quick.
- What happens behind the scene? behind the scenes, what LinkedIn is going to do is before it try to enable this button on this website, they will approach to the Google team and express their interest in using social login in the LinkedIn website, and Google team, what they're going to say, they'll say, "Um fine, you just have to create your own details inside my Auth server so that the integration between Auth server and LinkedIn is going to work seamlessly."
- So if you go to the official [documentation](https://developers.google.com/identity/protocols/oauth2) of OAuth 2.0 from Google inside this website, you'll be able to see what are the steps that any organization has to follow if they want to use Google as an auth server inside their websites.

![alt text](image-11.png)

![alt text](image-7.png)

- So first, the organization like LinkedIn, they have to register themselves to get the client ID and their client secret. So these credentials are different from the end user credentials. These client ID and client secret, they're going to be used by the Google Auth server to identify which client application is trying to invoke the authentication or authorization process. Here, the client is going to be LinkedIn, so LinkedIn is going to get its own ID and secret, which it has to use while it is trying to invoke the authentication process here. So here, if you click on the sign in with the Google, it is going to redirect you to the Google login page. You can see the domain, `accounts.google.com`, and here there is a client ID which, so this client ID represents LinkedIn. So based upon this client ID only, Google is trying to give your information that sign in with your Gmail so that you can continue to use LinkedIn. So this LinkedIn information is coming based upon this client ID. As soon as you click Next, google will take your consent which states like **by continuing Google will share my name, email address, language preference, profile picture with the LinkedIn**.
- So here, as soon as you click continue, behind the scenes an access token will be issued by Auth server. The same will be sent back to the LinkedIn, and LinkedIn will land me onto their website.

![alt text](image-8.png)

![alt text](image-9.png)


## Grant Type - Authorization Code 


![alt text](image-10.png)


- **Authorization code grant type flow must be used when an end user is involved during the authentication process. If there are no credentials that are going to be entered by end user, then this is not the grant type flow you need to use**.
- Whenever we try to adopt authorization code grant type flow. Inside this grant type flow, There'll be four different components or parties involved. The very first party is end user. End user, in other words, we can also call as Resource Owner. The second component is Client application followed by Auth Server, followed by Resource Server. So this Client application and Resource Server can belong to different organizations, or they can belong to the same organization. Regardless of their relationship, this authorization code grant type flow, it's going to work very similarly.
- In the very first step, the end user or the Resource Owner, he'll go to the Client application, and he'll try to perform some action which indicates that he want to access his resources. So this Client application can be a mobile application or a UI application. Whenever an end user requested a resource to a Client application, the Client application will simply respond to the end user inside the step two saying that, please work with the Auth Server and tell the Auth Server that you are fine me doing an action on your behalf. So how this is going to happen? Simply the Client application is going to redirect the end user to the login page of the Auth Server. 
- Inside the step three, the Resource Owner or the end user is going to tell to the Auth Server, please allow this client to access my resources. But that Auth Server is going to say, hey, I'm fine doing that. But you as an end user are a Resource Owner so first you prove your identity. So how the end user is going to prove his identity? He's going to enter the username and password inside the login page of the Auth Server. If the credentials that are entered inside the step three are valid, as part of the step four, the Auth Server is going to send a response to the client application saying that so-and-so end user seems to be fine to access his resources, so here is your authorization code. **So this is not an access token. This is a temporary authorization code that is going to be issued by the Auth Server to the client application**. So as part of the step three, the end user proved his identity.
- As a next step, the client also has to prove its identity by sharing its client secret. So that's why as part of the step five, what is going to happen is the client application, it is going to send a request saying that, here are my client credentials. When you say client credentials, it will be **client_id** and **client_secret**. So both of them we can together call them as **client credentials**. Along with the client credentials, it should also send the same Authorization Code that it has received as part of step four.
- So by providing all these details, **it is going to request the auth server to issue an access token**. If all the details are valid, as part of the step six, the Auth Server, it is going to issue an access token to the client application. By the time the step six is completed, now the client application has the access token. So now using this access token, it is going to call the Resource Server saying that I want to access so-and-so end user resources, and we know resource server won't be giving the client if its ask directly, that's why the client worked with the Auth server and got an access token, and this is the access token for your reference. So that is what is going to happen as part of step seven. 
- So inside the step eight, the Resource Server is going to validate if the access token is valid or not. If the access token is valid, it is going to send the actual resources requested by the client application as a response. So these resource detail will eventually display to the end user. So this is how the authorization code grant type flow works.
- But how the Resource Server is going to validate the access token is issued by the Auth Server? this token can be issue by any other server right? there is no direct communication between resource and auth server? it is done using **JWT Token**. Inside the JWT tokens, there is a digital signature concept. Using the digital signature concept, one can validate if the token is valid or not by them self without the need of reaching out to the token-issuing component.
- Okay but the client may have other products or pages , now how auth server will which of its pages does the user needs to be landed up? the answer is based on the request format that Client application is going to send to the Auth Server.

![alt text](image-12.png)

- **As a part of step 2 & 3**, where the end user identity is going to be verified by the Auth Server, what client application it is going to do is, it is going to send the **client_id only**. So using this **client_id**, the Auth Server can identify the details of the client application, and the same details, it is going to show you on the login page of the auth server saying that so-and-so client is trying to access your resources. Are you fine with that? So this kind of consent the Auth Server is going to show based upon this client_id that it received as part of step two and three.
- Apart from client_id, the client application, it is also going to send other details like **redirect_uri**, scope, state, and response_type. The purpose of **redirect_uri** is very simple. Inside these request parameter only, the client application is going to mention what is the URI value that the Auth Server needs to redirect post to end user's successful authentication. And inside the scope, the client application is also going to mention what are the authorities or what are the level of access that the client is looking inside the Resource Server.  For example, if the client is looking for the READ scope. So inside the state request parameter, we are going to send a randomly generated CSRF token value. So this is to protect from the CSRF attacks. At last, inside the **response_type**, the value **code** is going to be mentioned, because as part of the step two and three, the end user identity is going to be verified. If the end user identity is verified, the client application is expecting an **Authorization Code** as its response
- The Auth Server, it is going to redirect the response to the **redirect_uri** with the Authorization Code. Once the Authorization Code is received by the client application, what it is going to do is, **as part of the step five**, it is going to prove its own identity. So that's why this time inside the request, it is going to send both the **client_id** and **client_secret**. Along with these client_id and client_secret, it is going to send the same Authorization Code value that it has received as part of step four. And this time, it should also mention **grant_type** inside the request. The value of **grant_type** should be **Authorization Code**, because we are expecting an access token belongs to an end user. So that's why we need to mention this valid value, which is Authorization Code. Apart from grant_type, it is also going to send the redirect_uri, which is going to have the URI details that Auth Server can be used to redirect the response after issuing an access token. So whatever CSRF token value that we have mentioned initially under this state, it is going to play a very important role to avoid the CSRF attacks.
- If you see the flow, **why in the Authorization Code grant type flow, client is making requests two times**? The very first time it is trying to make a request to get the Authorization Code, followed by another request to get an access token.
- Lets take a demo of it. So on internet there is a [OAuth2 playground](https://www.oauth.com/playground/index.html) available which help you understand the OAuth authorization flows and show each step of the process of obtaining an access token.
- So now first we need to have a client and a user details.

<video controls src="20240907-1658-41.5085194.mp4" title="Title"></video>

- So here we have a user details like email id **muddy-worm@example.com** and password **Inquisitive-Pig-95** and client details like id **YKd9xgF9JEkrUfBwc97fyeMq** and client secret **UzfP3D5v8xNen82j09dW7ofgLm0O61dITG-77YLCCKzeoXIl**. Now this playground directly takes the client credentials just for demonstration, now click on **Back to flows**.

<video controls src="20240907-1702-30.7567721.mp4" title="Title"></video>

- Now in the real time, client will built a authorization url or a request, it consist of below things.

```
&client_id=YKd9xgF9JEkrUfBwc97fyeMq
  &redirect_uri=https://www.oauth.com/playground/authorization-code.html
  &scope=photo+offline_access
  &state=3bVTRpV0nbHf173k
```

- Here instead of CSRF token, a random string is generated for the **state**. If you observe here the **client_secret** is not specified because first the user credentials authentication occurs (**step 3 in the image**).

<video controls src="20240907-1710-07.8788599.mp4" title="Title"></video>

- When we click on **Authorize** , we need to enter the user credentials and the auth server takes our consent. Now if we see the url,

```
https://www.oauth.com/playground/authorization-code.html?state=3bVTRpV0nbHf173k&code=wXh2iNZq7ozeBYhKbzB6W9sItCFVxJZ1ZaXaUOB-LVv9E36H
```

- This consist of **state** value and a authorization code `code=wXh2iNZq7ozeBYhKbzB6W9sItCFVxJZ1ZaXaUOB-LVv9E36H` present in it. You need to first verify that the state parameter matches the value stored in this user's session so that you protect against CSRF attacks. (**step 4 in the image**)
- Now click on **It Matches! Continue**.

![alt text](image-13.png)

- Now the client is ready for exchange the authorization code for an access token. ( **step 5 in the image**). It prepares a post request which includes **client_secret** and **authorization code**. If you try to send all these details inside the get request as a query param then there won't be any security and there is a good chance that client application may lose their secret. That's why here, we are following the post. ( **step 6 in the image**)

```
POST https://authorization-server.com/token

grant_type=authorization_code
&client_id=YKd9xgF9JEkrUfBwc97fyeMq
&client_secret=UzfP3D5v8xNen82j09dW7ofgLm0O61dITG-77YLCCKzeoXIl
&redirect_uri=https://www.oauth.com/playground/authorization-code.html
&code=wXh2iNZq7ozeBYhKbzB6W9sItCFVxJZ1ZaXaUOB-LVv9E36H
```

- Now when we click on **Go**. We get the token and its expiration time.

![alt text](image-14.png)

## Grant Type - Implicit Grant (Deprecated)

![alt text](image-15.png)


- The flow is similar to authorization code but here when we validate the user credentials, at that time instead of sending the authorization code we sent directly the token. Now this becomes the risk factor, how ?

![alt text](image-16.png)

- Here if you see there is no **client_secret** present in the request when the client sents request to auth server. The request type is **Get**. The reason why client secret is not involved is, since this is a GET request, inside the GET request, there is no meaning of sending the client secret. That's why this flow does not support client secret as part of the step three request. And with that what is going to happen? Anyone who knows the client_id, they will be able to mimic as a client application with the auth server, and once the end user entered his credentials, they should be able to get the access token. So since there is no way for the auth server inside this flow to validate the identity of the client application, this is marked as deprecated.
- Apart from these drawback, the other serious drawback is when the auth server is trying to share the access token with the client application, the access token is going to be shared inside the request URL itself because, initially, the request went using GET, so to this GET, as a response, the auth server can only send using GET only since it is going to send the access token inside the GET URL itself, there's a very good chance that anyone who has access to your browser history, they'll be able to steal your access token. Think of a scenario, one of the auth server is issuing an access token with an expiration of seven days.In this scenario, you might have used the access token on day one and you left the computer. If some other user has access to your browser history, they'll be able to easily know what is the access token that auth server sent initially. Using the same access token, there is a good chance that they may misuse it.

## Grant Type - PKCE (Proof Key For Code Exchange)

- PKCE OAuth2 flow is going to work very similar to authorization code grant type flow, but it has some minor differences. In other words, we can call this PKCE as another flavor of authorization code grant type flow. When we are discussing about the authorization code grant type flow, as part of the step five, the client application, they have to share their client credentials. So as part of these client credentials, they need to pass their **client_id** and the **client_secret**. But unfortunately, the client secret can't be saved by the public clients like JavaScript-based applications, single page applications which are built based upon angular reactive framework, mobile applications. Because all the client code, it is going to be built with the help of plain JavaScript and anyone can see the source code inside the browser or by downloading the mobile application installation file. And with that, they'll be able to see the client secret very clearly. That's why for public-facing clients, where they're going to build a code with the help of JavaScript for such applications, instead of following the authorization code grant type flow, they follow **PKCE** grant type flow.

<details>

<summary> Why JavaScript-based applications can't securely use the client secret in the Authorization Code Grant type flow ? </summary>

- The main reason JavaScript-based applications, like single-page applications (SPAs) or mobile applications, can't securely use the client secret in the Authorization Code Grant type flow is due to the nature of these applications being **public clients**.
- **Public Clients**: These are applications that run on the end user's device and cannot securely store secrets. This includes JavaScript-based SPAs, mobile apps, and desktop apps.
- **Confidential Clients**: These are server-based applications that can securely store secrets, such as backend servers where the code is not exposed to the end user.
- In the traditional Authorization Code Grant flow, the client must send its client_id and client_secret to the authorization server to exchange the authorization code for an access token.
- In a JavaScript-based application, the source code is exposed to the end user. This means that if you include the client_secret in your JavaScript code, anyone can view the source code in the browser's developer tools or by inspecting the mobile application package.
- Even if you try to pass it via code, the **client_secret** in your JavaScript code or pass it in some other way, it is still vulnerable to being extracted by anyone with access to the code. JavaScript code running in the browser or mobile app is not secure, and any secret embedded within it can be easily compromised.
- This is why PKCE is recommended for public clients, as it provides a secure way to handle authorization without requiring a client secret.

</details>



![alt text](image-17.png)


- Since these public clients cannot securely store the client secret, there is a workaround provided inside the OAuth 2.0 to standard with the help of **Proof Key for Code Exchange** flow. So let's try to understand what is going to happen inside the PKCE flow.


![alt text](image-18.png)


- Whenever an end user clicks on the login button or whenever he tried to initiate an operation inside the client application, then we know the client application, it is going to redirect the end user to the Auth Server login page. So during this redirection, behind the scenes the client application, what it is going to do is it is going to generate two different values.
- One is **code_verifier** and the other one is **code_challenge**. So using this code verifier only, this code challenge is going to be derived. For example, if the code verifier value is 123, using this 123 only, the code challenge is going to be derived by applying the SHA256 hash function. And once the hashing function is completed, it is going to be base Base64-URL-encoded.
- So once that code verifier and code challenge is generated by the client application, during the very first step where it is trying to redirect the end user to the login page of the Auth Server, in this very first step, the client application, it is only going to share the code challenge with the Auth Server. So inside the request parameters, along with the client ID, it is also past the code challenge. This code challenge is going to be saved by the Auth Server behind the scenes.
- So once the end user authentication is successful, we know the Auth Server is going to send back the response to the client application along with the authorization code. So now using this authorization code, the client application has to prove its identity. So how it is going to prove its identity is this time while it is trying to request for the access token, along with the authorization code that it has received, it is also going to send the code verifier that is generated initially in the very first step on the client side.
- Using this code verifier, the Auth Server, it is going to apply the same hashing function on top of this code verifier, and with that whatever initial value that it has received as part of code challenge, it has to be equally same; otherwise the Auth Server is not going to issue the access token.
- This way, Auth Server is making sure that whatever client application that initially initiates the request in the very first step, the same client application has to get the access token.

![alt text](image-19.png)

- So here we are passing request two times, again if we pass only request once we will landed up into **implicit grant type**.
- So here you may have a question which is **why code challenge is being shared first followed by code verifier at later point of time**? We know the **code_challenge is a hash representation of code_verifier**. This code challenge or hash value is going to be shared to the Auth Server so that in between, if someone steals this code challenge it is not going to make any sense to them because using this code challenge, they cannot derive the code verifier because it's a hash value. Only the client application which generates this code challenge, they will only know this code verifier plain text value.
- So once all the validations are completed on the Auth Server, it is going to issue an access token, ID token, refresh token, based upon what is being requested by the client application. This way, though there is no client credential involved inside the authentication flow, the Auth Server will make sure that it is issuing an access token to only the original client application who initiates the authentication flow.
- Let's see the demo of this PKCE flow very quickly. Inside this playground website,

<video controls src="20240907-1906-49.9584738.mp4" title="Title"></video>

- In the very first step, the client application, it has to generate the **code_verifier** and the **code_challenge** using SHA256 algorithm and base64 encoding.
- Now the client application generated both code_verifier and code_challenge and it is going to store these details somewhere inside the request.

<video controls src="20240907-1908-38.2811828.mp4" title="Title"></video>

```
https://authorization-server.com/authorize?
  response_type=code
  &client_id=YKd9xgF9JEkrUfBwc97fyeMq
  &redirect_uri=https://www.oauth.com/playground/authorization-code-with-pkce.html
  &scope=photo+offline_access
  &state=fOy0SEuViBp6G8nx
  &code_challenge=GkaL6wD2kvxUQvmumFLQPIfJAKFCk8m-i6W1fRQ-yUc
  &code_challenge_method=S256
```


- Here it is going to send a get request. Inside this get request, the response type, it is going to mention as code client_id and the redirect_uri, scope, state. Coming to the code challenge, it is going to mention whatever code challenge it is calculated here, so the same code challenge it is trying to provide here. And the code challenge method also it has to provide which is SHA256.

<video controls src="20240907-1910-18.2477537.mp4" title="Title"></video>

- Here, once I click on this Log in button, ypu will get a consent, you will approve this consent. And with that, we'll go to the next step where the state parameter we need to validate. So this state parameter protects us from the CSRF attacks and inside the get response, we also receive the authorization code value.

```
?state=fOy0SEuViBp6G8nx&code=5BB1mnrOwStJevdcXmGt3KfNx0SVdKqCNxljO7yPbnapcZ_e
```

- Next time, the client application, it is going to make a POST request to get the access token. So as part of this POST request, it is going to mention the grant type as authorization code. So the grant type value is always going to be the same, regardless whether you're using the traditional authorization code grant type flow or the PKCE flow.

<video controls src="20240907-1912-50.4885824.mp4" title="Title"></video>

```
POST https://authorization-server.com/token

grant_type=authorization_code
&client_id=YKd9xgF9JEkrUfBwc97fyeMq
&client_secret=UzfP3D5v8xNen82j09dW7ofgLm0O61dITG-77YLCCKzeoXIl
&redirect_uri=https://www.oauth.com/playground/authorization-code-with-pkce.html
&code=5BB1mnrOwStJevdcXmGt3KfNx0SVdKqCNxljO7yPbnapcZ_e
&code_verifier=s0hISzySU_1BsO2ROd7cKTb5sjj5J-K1w-9fzJWop6XAJAB2
```

- How Auth Server will identify whether you are trying to use the PKCE flow or whether you're trying to use the authorization code grant type flow or combination of them, is based upon the request that you are going to send. So if the authorization code grant type flow sees the fields like code_verifier, code_request, code_challenge method. So if it sees all such information, then it is a confirmation to the authorization server that you are trying to use the PKCE flow. So after the grant type, we need to mention what is the client ID and the client secret (optional)
- So now using this code verifier,behind the scenes the authorization server, it is going to generate the code challenge by following the same formula that we have used inside the step one. If the initially shared code challenge is matching with the newly calculated code challenge, then only the authorization server is going to issue an access token.

![alt text](image-20.png)


## Grant Type - Password Grant

![alt text](image-21.png)


- Password Grant Type Flow, also called as Resource Owner Credentials Grant Type Flow. Inside this flow what is going to happen is that end user is going to hand over his credentials to the client application itself. What is going to happen is the client application, it is going to redirect the end user to the login page of the odd server to enter his credentials. But inside this flow, the client application itself is going to have their own login page where they are going to ask the end user credentials that he's maintaining inside the AUTH server. 
- So once the end user credentials are accepted by this client application, it is going to make a request to the AUTH server with the details of user credentials, client to credentials. 
- So both the client credentials and the end user credentials, it is going to send as part of the request to the AUTH server. If all the credentials are valid as part of the step three,the access token is directly going to be issued to the client application.
- Using this access token, the client application can make a request to the Resource Server. If the access token is valid, the resource server is going to respond with the proper successful response.
- So there is a very good chance that this client application may misuse the end user credentials. So before it is trying to invoke the AUTH server with the end user credential, the client application may store the end user credentials somewhere inside its own database. Since this serious drawback is present inside this Grant Type Flow, this is no more recommended for any production usage but still you will see some organizations using password Grant Type Flow. 
- The reason is this flow is very simple to follow, but whenever someone is following this flow, they'll make sure this client application and this AUTH server, Resource Server, they all belongs to the same organization. So in this kind of setup, there is no chance that this client application can misuse the credentials of the end user because both client and AUTH server belongs to the same organization or the same project.


## Grant Type - Client Credentials

![alt text](image-22.png)


- It is the most commonly used grant type flow inside the microservices environment. We need to use this grant type flow when now there is no end user is involved and when our two backend applications are two different APIs that are trying to communicate with each other. So this Resource Server can be a microservice and this client also can be another microservice, or some other backend application or backend API who are trying to talk with each other.
- But instead of directly allowing them to talk with each other, we are going to enforce security with the help of Client Credentials Grant Type Flow. Behind the scenes, what this client application has to do is it has to register with the auth server and it needs to get its own client credentials.
- So once this step is completed, the client application, it is going to resume client ID and client secret. So these values are going to act as username and password for this client whenever it want to get an token from the auth server.
- The client is going to approach the auth server to provide an access token and inside the request it is also going to mention its client credentials and since there is no user involved inside this, it's not going to share any end user-related details. If the client credentials are valid, the auth server, it is going to issue an access token to the client.
- Now the client, which can be a microservice or a backend API or a backend server, it can invoke another backend server or another backend API or microservice which is the resource server

![alt text](image-23.png)


## Grant Type - Refresh Token



- Whenever we are using Authorization Code Grant Type flow or PKCE Grant Type flow, inside the response we get two types of tokens. The very first one is **access_token** and the other one is **refresh_token**. There is a purpose for this **refresh_token**. Using this **refresh_token**, we can use Refresh Grant Type flow. 

![alt text](image-14.png)

- Let's try to understand the flow of this grant type flow by looking at the slides. The Refresh Token Grant Type flow, it is only going to be initiated by the client application behind the scenes without involving the end user. That's why here we have only three components, Client, Auth Server and Resource Server.
- The end user will only be involved in the scenarios where he has to enter his credentials during the initial authentication process. So let's imagine of a scenario where my client application does not know whether a given access token is expired or not. In this scenario, the client application, as usual, it is going to make a request to the Resource Server with the access_token. But this time, the Resource Server, since the access_token is expired, it is going to throw an error, which is 403 forbidden.
- So now my client application, it will reach out to the auth server in a hurry saying that, "Hey, auth server my access_token is expired, I want a new access_token. And here is the refresh_token that you have issued in the initial authentication process."
- If the refresh_token is valid, the auth server, it is going to issue a new access_token and new refresh_token. We also have an option of using the same refresh_token always, but it is advisable to rotate the refresh_token as well where now these Refresh Token Grant Type flow is being invoked.
- Now using the access_token that it has resolved inside the step four, my client application, it is going to make a request to the Resource Server. This time, since the access_token is valid, the Resource Server, it is going to respond with the proper response which can be processed by the client application.


![alt text](image-24.png)

- So what details we are going to send as part of step three, the client application, it is going to send the client_id and client_secret, along with the refresh_token that it has resolved during the initial authentication process. The purpose of the scope you already know, coming to the grant_type. This time we need to mention the value as **refresh_token**.
- So this is an indication to the auth server that Refresh Token Grant Type flow is initiated. With this grant type flow behind the scenes, the auth server, it is going to expect the value under the refresh_token.
- Why can't we make an access_token, which is never going to be expired? So that we can avoid extra complexity around the refresh_token. If we issue an access_token with an unlimited time, then it is going to be as good as end user credentials. So anyone who has this access_token, they'll be able to use these forever because the token is never going to be expired. So to avoid these kind of security related issues, always the access_token, they are going to be issued with a short duration. Most of the times it is going to be issued with 24 hours time, which will work for most of the applications. But if your application is a super critical application, like a bank application, then obviously you can't issue an access_token with an expiration of 24 hours. You need to reduce the time to one hour or 30 minutes based upon your business requirements.
- Then Why can't we make our refresh_token to never expire? though it is possible but it is not recommended considering the scenarios where the refresh tokens can be stolen by some bad users.