
/**
 * Tool to retrieve the location of the Elixir Language Server.
 */
public interface LanguageServerLocator {
	/**
	 * @return handle to the Elixir-LS directory
	 */
	String getServerLocation();
}
