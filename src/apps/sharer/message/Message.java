package apps.sharer.message;

public interface Message {

	boolean isLastInConversation();

	MessageType getType();
}
