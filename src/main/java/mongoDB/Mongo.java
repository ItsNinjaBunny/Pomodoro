
package mongoDB;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class Mongo {
	
	
	
	public MongoClient connectDatabase(String username) {
		final String connection = "mongodb://user_1:Passw0rd1@pomodoro-shard-00-01.jnyc8.mongodb.net:27017/" + username + "?ssl=true&replicaSet=atlas-nqzfih-shard-0&authSource=admin&retryWrites=true";
		
		MongoClientURI uri = new MongoClientURI(connection);
		MongoClient mongoClient = new MongoClient(uri);
		
		return mongoClient;
	}
	
	public Mongo() {
		
	}
}
