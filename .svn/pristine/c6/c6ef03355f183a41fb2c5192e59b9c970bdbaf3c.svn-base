package com.uslc.po.gui.master.catalog;

import org.eclipse.swt.widgets.Composite;
import com.uslc.po.gui.master.Master;
import com.uslc.po.gui.master.MasterSections;
import com.uslc.po.gui.master.interfaces.LiveDataAccessLifeCicle;
import com.uslc.po.jpa.test.UslcJpaManager;

public class FormCenterMaster extends Composite {
	private MasterSections parent = null;
	
	public FormCenterMaster(MasterSections parent, int style) {
		super(parent.getMaster().getHiddenShell(), style);
		this.parent = parent;
		getParent().addOpenedComposite( this );
	}
	
	public MasterSections getParent(){
		return parent;
	}
	public Master getMaster() {
		return getParent().getMaster();
	}
	public void setLastAction( String text, FormCenterMaster form ) {
		getMaster().setLastAction(text, form );
	}
	
	private void clean(){
	}
	public LiveDataAccessLifeCicle getLiveDataAccessLifeCicle() {
		return null;
	}
	public UslcJpaManager getUslcJpaManager() {
		return getMaster().getLiveDataAccess().getUslcData();
	}
	public void cleanInfoText(){
		getParent().getMaster().cleanInfoText();
	}
	public void setInfoText( InfoForm info ) {
		getParent().getMaster().getMasterRight().setInfoText(info);
	}
	public void hide(){
		clean();
		this.setParent( getParent().getMaster().getHiddenShell() );
		this.setVisible(false);
		
	}
	
	public class InfoForm {
		private String title = "";
		private String desc = "";
		private String[] features = null;
		
		public InfoForm( String title, String desc, String[] features ) {
			this.title = title;
			this.desc = desc;
			this.features = features;
		}

		public String getTitle() {
			return title;
		}
		public String getDesc() {
			return desc;
		}
		public String[] getFeatures() {
			return features;
		}
	}

	public void updateLiveDataAccess(){
		getMaster().updateLiveDataAccess();
	}
}
