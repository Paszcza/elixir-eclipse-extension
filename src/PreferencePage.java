import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class PreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public PreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Elixir Extension Preferences");
	}

	@Override
	protected void adjustGridLayout() {
		super.adjustGridLayout();
		GridLayout layout = (GridLayout) getFieldEditorParent().getLayout();
		layout.verticalSpacing = 15;
		layout.marginHeight = 10;
		layout.marginWidth = 10;
	}

	@Override
	protected void createFieldEditors() {
		createElixirGroup();
		createLanguageServerGroup();
	}
	
	private void createElixirGroup() {
		Group elixirGroup = createGroup(getFieldEditorParent(), "Elixir Runtime", 1);

		FileFieldEditor elixirPathEditor = new FileFieldEditor(Preferences.ELIXIR_PATH, "Location:", elixirGroup);
		addField(elixirPathEditor);
	}
	
	private void createLanguageServerGroup() {
		Group languageServerGroup = createGroup(getFieldEditorParent(), "Elixir Language Server", 3);

		BooleanFieldEditor bundledServerToggle = new BooleanFieldEditor(Preferences.USE_BUNDLED_LS, "Use bundled Elixir-LS",
				languageServerGroup);
		addField(bundledServerToggle);

		Composite proxyComposite = new Composite(languageServerGroup, SWT.NONE);
		proxyComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		FileFieldEditor languageServerPathEditor = new FileFieldEditor(Preferences.LS_PATH, "Location:", proxyComposite);
		languageServerPathEditor.setEnabled(!getPreferenceStore().getBoolean(Preferences.USE_BUNDLED_LS), proxyComposite);
		addField(languageServerPathEditor);

		Label elixirLSLabel = new Label(languageServerGroup, SWT.LEFT);
		elixirLSLabel.setText("Restart Eclipse to establish new connection with the server");
		
		setupBundledServerToggleListener(bundledServerToggle, languageServerGroup, languageServerPathEditor, proxyComposite);
	}
	
	private void setupBundledServerToggleListener(
			BooleanFieldEditor bundledServerToggle, Composite bundledServerToggleParent,
			FileFieldEditor serverPathEditor, Composite serverPathEditorParent
			) {
		Control bundledServerToggleControl = bundledServerToggle.getDescriptionControl(bundledServerToggleParent);
		bundledServerToggleControl.addListener(SWT.Selection, e -> {
			serverPathEditor.setEnabled(!bundledServerToggle.getBooleanValue(), serverPathEditorParent);
		});
	}

	private static Group createGroup(Composite parent, String text, int numColumns) {
		Group group = new Group(parent, SWT.NONE);
		group.setText(text);
		GridLayout layout = new GridLayout(numColumns, false);
		group.setLayout(layout);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		group.setLayoutData(gridData);
		return group;
	}

	@Override
	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub
	}

}
