package cs275.langfinder.svcclient;

public class ServiceController {
	String serviceRoot;

	public String getServiceRoot() {
		return serviceRoot;
	}

	public ServiceController(String serviceRoot) {
		this.serviceRoot = serviceRoot;
	}
}
