
package mongoDB;

import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;

import com.mongodb.BasicDBObject;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
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
			documents.add(new String[] {String.valueOf(doc.get("sessionID")), String.valueOf(doc.get("sessionLength")), String.valueOf(doc.get("sessionDate"))});
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

	public void logSession(String username, int session) {
		final String logs = "logs";

		MongoClient mongoClient = connectDatabase();
		MongoDatabase database = mongoClient.getDatabase(username);
		MongoCollection<Document> collection = database.getCollection(logs);

		int count = (int) collection.count() + 1;
		String str = String.valueOf(session);
		str = str + "00";
		final int mid = str.length() / 2; //get the middle of the String
		String[] parts = {str.substring(0, mid),str.substring(mid)};
		String sessionLength = parts[0] + ":" + parts[1];

		Document doc = new Document();
		doc.append("_id", count).append("sessionID", count).append("sessionLength", sessionLength).append("sessionDate", String.valueOf(java.time.LocalDate.now()));

		collection.insertOne(doc);

		mongoClient.close();
	}

	public void completedTask(String username, String item) {
		final String tasks = "tasks";

		MongoClient mongoClient = connectDatabase();
		MongoDatabase database = mongoClient.getDatabase(username);
		MongoCollection<Document> collection = database.getCollection(tasks);

		String date = String.valueOf(java.time.LocalDate.now());
		collection.updateOne(Filters.eq("task name", item), Updates.set("date completed", date));

		mongoClient.close();
	}
}
