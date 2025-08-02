import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import org.eclipse.lsp4e.server.StreamConnectionProvider;

public class LanguageServerConnectionProvider implements StreamConnectionProvider {

	private Process process;

	@Override
	public void start() throws IOException {
		ProcessBuilder processBuilder = createProcessBuilder();
		processBuilder.redirectErrorStream(true);
		process = processBuilder.start();
	}

	private static ProcessBuilder createProcessBuilder() {
		String serverLaunchScriptPath;
		if (Activator.getDefault().getPreferenceStore().getBoolean("USE_BUNDLED_LS")) {
			serverLaunchScriptPath = LanguageServerLocators.BundledLS.getServerLocation();
		} else {
			serverLaunchScriptPath = Activator.getDefault().getPreferenceStore().getString(Preferences.LS_PATH);
		}
		ProcessBuilder processBuilder = new ProcessBuilder(serverLaunchScriptPath);
		return processBuilder;
	}

	@Override
	public InputStream getInputStream() {
		return process.getInputStream();
	}

	@Override
	public OutputStream getOutputStream() {
		return process.getOutputStream();
	}

	@Override
	public void stop() {
		if (process != null && process.isAlive()) {
			process.destroy();
		}
	}

	@Override
	public Object getInitializationOptions(URI rootUri) {
		return null; // Or return init options
	}

	@Override
	public InputStream getErrorStream() {
		return null;
	}

}
