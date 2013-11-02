import java.io.IOException;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;


public class createInstance {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		AWSCredentials credentials = new PropertiesCredentials(createInstance.class.getResourceAsStream("AwsCredentials.properties"));
		
		AmazonEC2Client amazonEC2Client = new AmazonEC2Client(credentials);
		
		amazonEC2Client.setEndpoint("ec2.us-west-2.amazonaws.com");
		
		DescribeInstancesRequest describeinstance=new DescribeInstancesRequest();
		
		System.out.println(describeinstance);

	}

}
