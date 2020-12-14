// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

import com.microsoft.graph.models.extensions.IGraphServiceClient;
import com.microsoft.graph.models.extensions.User;
import com.microsoft.graph.requests.extensions.GraphServiceClient;
import com.microsoft.graph.requests.extensions.IUserCollectionPage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import com.microsoft.graph.auth.confidentialClient.ClientCredentialProvider;
import com.microsoft.graph.auth.enums.NationalCloud;

class ClientCredentialGrant {

    private static String tenant;
    private static String clientId;
    private static String secret;
    private static String scope;

    public static void main(String args[]) throws Exception{

        setUpSampleData();
        List<String> scopes = new ArrayList<>(Arrays.asList(scope) );
        

        try {
            

        	ClientCredentialProvider authProvider = new ClientCredentialProvider(
                    clientId,
                    scopes,
                    secret,
                    tenant,
                    NationalCloud.Global);
        	IGraphServiceClient graphClient = GraphServiceClient.builder().authenticationProvider( authProvider ).buildClient();

        	
        	IUserCollectionPage usersListFromGraph = graphClient.users()
        		.buildRequest()
        		.get();

        	System.out.println("Users in the Tenant = ");
        	for ( User user: usersListFromGraph.getCurrentPage() ) {
        		System.out.println(user.userPrincipalName);
        	}
            System.out.println("Press any key to exit ...");
            System.in.read();

        } catch(Exception ex){
            System.out.println("Oops! We have an exception of type - " + ex.getClass());
            System.out.println("Exception message - " + ex.getMessage());
            throw ex;
        }
    }


    /**
     * Helper function unique to this sample setting. In a real application these wouldn't be so hardcoded, for example
     * different users may need different scopes
     */
    private static void setUpSampleData() throws IOException {
        // Load properties file and set properties used throughout the sample
        Properties properties = new Properties();
        properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"));
        tenant = properties.getProperty("TENANT");
        clientId = properties.getProperty("CLIENT_ID");
        secret = properties.getProperty("SECRET");
        scope = properties.getProperty("SCOPE");
    }
}
