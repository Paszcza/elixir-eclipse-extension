import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.ILaunchShortcut;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;

public class LaunchShortcut implements ILaunchShortcut {

	@Override
	public void launch(ISelection selection, String mode) {
		if (selection instanceof IStructuredSelection structuredSelection) {
			Object firstSelectionElement = structuredSelection.getFirstElement();
			if (firstSelectionElement instanceof IFile file) {
				try {
					ILaunchConfigurationType type = DebugPlugin.getDefault().getLaunchManager()
							.getLaunchConfigurationType(Activator.PLUGIN_ID + ".launchType");

					ILaunchConfigurationWorkingCopy config = type.newInstance(null, file.getName());
					config.setAttribute("codeFilePath", file.getLocation().toString());
					config.launch(mode, null);
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void launch(IEditorPart editor, String mode) {
		IEditorInput input = editor.getEditorInput();
		IFile file = input.getAdapter(IFile.class);
		if (file != null) {
			launch(new StructuredSelection(file), mode);
		}
	}
}
