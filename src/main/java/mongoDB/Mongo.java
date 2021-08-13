
package mongoDB;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

public class Mongo {
	
	final String connection = "mongodb://user_1:Passw0rd1@pomodoro-shard-00-01.jnyc8.mongodb.net:27017/users?ssl=true&replicaSet=atlas-nqzfih-shard-0&authSource=admin&retryWrites=true";
	
	
	public void databaseConnection(String username) {
		
		final String dbName = "users";
		
		MongoClientURI uri = new MongoClientURI(connection);
		MongoClient mongoClient = new MongoClient(uri);
		MongoDatabase database = mongoClient.getDatabase(dbName);
		String name = (database.getCollection(username).getNamespace()).toString().replace("users.", "");
		
		mongoClient.close();
	}
}
