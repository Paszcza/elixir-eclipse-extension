import java.io.File;
import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.eclipse.debug.core.model.IProcess;

public class LaunchDelegate implements ILaunchConfigurationDelegate {
	
	@Override
	public void launch(ILaunchConfiguration configuration, String mode,
		ILaunch launch, IProgressMonitor monitor) throws CoreException {
			
		String codeFilePath = configuration.getAttribute("codeFilePath", (String) null);
		if (codeFilePath == null) {
			throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "File path is null"));
		}
		
		File codeFile = new File(codeFilePath);
		if (!codeFile.exists()) {
			throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "File does not exist"));
		}
		
		
		ProcessBuilder pb = new ProcessBuilder(Activator.getDefault().getPreferenceStore().getString("ELIXIR_PATH"), codeFilePath);
		pb.redirectErrorStream(true);
		try {
			Process process = pb.start();
			@SuppressWarnings("unused")
			IProcess eclipseProcess = DebugPlugin.newProcess(launch, process, codeFilePath);
		} catch (IOException e) {
			throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Failed to run Elixir", e));
		}
	}
}
