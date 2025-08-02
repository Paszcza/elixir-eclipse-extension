import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class Activator extends AbstractUIPlugin {

	public static final String PLUGIN_ID = "elixir-eclipse-extension";
	private static Activator plugin;

	public Activator() {}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		getPreferenceStore().setDefault(Preferences.USE_BUNDLED_LS, true);
		getPreferenceStore().setDefault(Preferences.ELIXIR_PATH, "/C:/Program Files/Elixir/bin/elixir.bat");
		getPreferenceStore().setDefault(Preferences.LS_PATH, LanguageServerLocators.BundledLS.getServerLocation());
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	public static Activator getDefault() {
		return plugin;
	}
}
