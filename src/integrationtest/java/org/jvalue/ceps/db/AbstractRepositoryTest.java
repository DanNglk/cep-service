package org.jvalue.ceps.db;


import org.ektorp.CouchDbInstance;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;
import org.junit.After;
import org.junit.Before;

public abstract class AbstractRepositoryTest {

	private final String databaseName;
	private final CouchDbInstance couchdbInstance;

	public AbstractRepositoryTest(String databaseName) {
		this.databaseName = databaseName;
		this.couchdbInstance = new StdCouchDbInstance(new StdHttpClient.Builder().build());
	}


	@Before
	public final void createDatabase() {
		createDatabase(couchdbInstance, databaseName);
	}


	protected abstract void createDatabase(CouchDbInstance couchdbInstance, String databaseName);


	@After
	public final void deleteDatabase() {
		couchdbInstance.deleteDatabase(databaseName);
	}


}
