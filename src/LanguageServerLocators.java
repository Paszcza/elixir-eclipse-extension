import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.SWT;
import org.osgi.framework.Bundle;

/**
 *  Stores implementations of {@link LanguageServerLocator LanguageServerLocator}
 */
public enum LanguageServerLocators implements LanguageServerLocator {
	/**
	 * locates Elixir-LS instance included in the plug-in bundle
	 */
	BundledLS {
		@Override
		public String getServerLocation() {
			Bundle bundle = Platform.getBundle(Activator.PLUGIN_ID);
			URL fileURL = FileLocator.find(bundle, new Path("elixir-ls"), null);
			URL resolvedFileURL;
			try {
				resolvedFileURL = FileLocator.toFileURL(fileURL);
			} catch (IOException e) {
				resolvedFileURL = null;
				e.printStackTrace();
			}
			return resolvedFileURL.getPath() + "language_server" + getExecutableType();
		}
		private String getExecutableType() {
			String suffix;
			if(SWT.getPlatform() == "win32") {
				suffix = ".bat";
			} else suffix = ".sh";
			return suffix;
		}
	}
}
