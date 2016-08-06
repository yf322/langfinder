package cs275.langfinder.data;

public class UserConnection {
	Integer id;
	Integer user1Id;
	Integer user2Id;
	String inviteMessage;
	boolean confirmed;
	boolean ignored;
	Integer complementUserConnectionId;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUser1Id() {
		return user1Id;
	}
	public void setUser1Id(Integer user1Id) {
		this.user1Id = user1Id;
	}
	public Integer getUser2Id() {
		return user2Id;
	}
	public void setUser2Id(Integer user2Id) {
		this.user2Id = user2Id;
	}
	public String getInviteMessage() {
		return inviteMessage;
	}
	public void setInviteMessage(String inviteMessage) {
		this.inviteMessage = inviteMessage;
	}
	public boolean getConfirmed() {
		return confirmed;
	}
	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}
	public boolean getIgnored() {
		return ignored;
	}
	public void setIgnored(boolean ignored) {
		this.ignored = ignored;
	}
	public Integer getComplementUserConnectionId() {
		return complementUserConnectionId;
	}
	public void setComplementUserConnectionId(Integer complementUserConnectionId) {
		this.complementUserConnectionId = complementUserConnectionId;
	}
	public UserConnection() {
		super();
	}
	public UserConnection(Integer id, Integer user1Id, Integer user2Id,
			String inviteMessage, boolean confirmed, boolean ignored,
			Integer complementUserConnectionId) {
		super();
		this.id = id;
		this.user1Id = user1Id;
		this.user2Id = user2Id;
		this.inviteMessage = inviteMessage;
		this.confirmed = confirmed;
		this.ignored = ignored;
		this.complementUserConnectionId = complementUserConnectionId;
	}

}
