
package mongoDB;

import javax.swing.DefaultListModel;

import com.mongodb.client.model.Sorts;
import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.List;

public class Mongo {
	
	public Mongo() {
		
	}
	
	public MongoClient connectDatabase() {
		
		final String connection = "mongodb://user_1:Passw0rd1@pomodoro-shard-00-01.jnyc8.mongodb.net:27017/users?ssl=true&replicaSet=atlas-nqzfih-shard-0&authSource=admin&retryWrites=true";
		
		MongoClientURI uri = new MongoClientURI(connection);

		return new MongoClient(uri);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getLogs(String username, DefaultListModel documents, List<String> sessionTimes) {
		
		MongoClient mongoClient = connectDatabase();
		MongoDatabase database = mongoClient.getDatabase(username);
		MongoCollection<Document> collection = database.getCollection("logs");
		
		FindIterable<Document> iterDoc = collection.find().sort(Sorts.ascending("sessionID"));

		for (Document tempDoc : iterDoc) {
			documents.addElement("Session ID: " + String.valueOf(tempDoc.get("sessionID") + "\t Session Time: " + String.valueOf(tempDoc.get("sessionLength"))));
			String session = String.valueOf(tempDoc.get("sessionLength")).replace(":", ".");
			sessionTimes.add(session);
		}
		
		mongoClient.close();
	}
}
