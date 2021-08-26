
package mongoDB;

import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;

import com.mongodb.client.model.Sorts;
import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
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
	public void getLogs(String username, ArrayList<String[]> documents, List<String> sessionTimes) {
		
		MongoClient mongoClient = connectDatabase();
		MongoDatabase database = mongoClient.getDatabase(username);
		MongoCollection<Document> collection = database.getCollection("logs");
		
		FindIterable<Document> iterDoc = collection.find().sort(Sorts.ascending("sessionID"));

		for (Document doc : iterDoc) {
			documents.add(new String[] {String.valueOf(doc.get("sessionID")), String.valueOf(doc.get("sessionLength"))});
			String session = String.valueOf(doc.get("sessionLength")).replace(":", ".");
			sessionTimes.add(session);
		}
		
		mongoClient.close();
	}

	public void getTasks(String username, ArrayList<String[]> documents) {
		final String tasks = "tasks";

		MongoClient mongoClient = connectDatabase();
		MongoDatabase database = mongoClient.getDatabase(username);
		MongoCollection<Document> collection = database.getCollection(tasks);



		FindIterable<Document> iterDoc = collection.find().sort(Sorts.ascending("_id"));
		for(Document doc : iterDoc) {

			documents.add(new String[] { String.valueOf(doc.get("_id")), String.valueOf(doc.get("task name")), String.valueOf(doc.get("date added")), String.valueOf(doc.get("date completed"))});

		}
		mongoClient.close();
	}

	public void insertTask(ArrayList<String[]> taskLog, String username, String task, String date) {
		final String tasks = "tasks";

		MongoClient mongoClient = connectDatabase();
		MongoDatabase database = mongoClient.getDatabase(username);
		MongoCollection<Document> collection = database.getCollection(tasks);

		int count = (int) collection.count();
		Document doc = new Document();
		doc.append("_id", count+1).append("task name", task).append("date added", date).append("date completed", "---");
		collection.insertOne(doc);

		taskLog.add(new String[] { String.valueOf(doc.get("_id")), String.valueOf(doc.get("task name")), String.valueOf(doc.get("date added")), String.valueOf(doc.get("date completed"))});

		mongoClient.close();
	}
}
