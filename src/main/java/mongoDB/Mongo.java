
package mongoDB;

import java.util.Iterator;

import javax.swing.DefaultListModel;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Mongo {
	
	public Mongo() {
		
	}
	
	public MongoClient connectDatabase() {
		
		final String connection = "mongodb://user_1:Passw0rd1@pomodoro-shard-00-01.jnyc8.mongodb.net:27017/users?ssl=true&replicaSet=atlas-nqzfih-shard-0&authSource=admin&retryWrites=true";
		
		MongoClientURI uri = new MongoClientURI(connection);
		MongoClient mongoClient = new MongoClient(uri);
		
		return mongoClient;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DefaultListModel getLogs(String username, DefaultListModel documents) {
		
		MongoClient mongoClient = connectDatabase();
		MongoDatabase database = mongoClient.getDatabase(username);
		MongoCollection<Document> collection = database.getCollection("logs");
		
		FindIterable<Document> iterDoc = collection.find();
		Iterator it = iterDoc.iterator();
		
		while(it.hasNext()) {
			Document tempDoc = (Document) it.next();
			
			Document doc = new Document();
			doc.append("SessionID", tempDoc.get("sessionID")).append("sessionLength", tempDoc.get("sessionLength"));
			documents.addElement(doc);
		}
		
		mongoClient.close();
		return documents;
	}
}
