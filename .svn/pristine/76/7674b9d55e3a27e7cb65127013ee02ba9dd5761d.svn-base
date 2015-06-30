package com.uslc.po.gui.master;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.swt.widgets.Composite;

import com.uslc.po.gui.master.catalog.FormCenterMaster;
import com.uslc.po.gui.master.interfaces.LiveDataAccessLifeCicle;
import com.uslc.po.jpa.test.UslcJpaManager;

public class MasterSections extends Composite {
	private Master master = null;
	private Set<Composite> openedComposites = null;

	public MasterSections(Master master, int style) {
		super(master, style);
		this.master = master;
		//initComposite();
		//getLiveDataAccessLifeCicle();
		getMaster().addOpenedMasterSections(this);
	}
	
	public Master getMaster() {
		return master;
	}
	protected void initComposite(){
	}
	public LiveDataAccessLifeCicle getLiveDataAccessLifeCicle() {
		return null;
	}
	public Set<Composite> getOpenedComposite() {
		if( openedComposites == null ) {
			openedComposites = new HashSet<Composite>();
		}
		return openedComposites;
	}
	public void addOpenedComposite( Composite composite ) {
		getOpenedComposite().add( composite );
	}
	public UslcJpaManager getUslcJpaManager() {
		return getMaster().getLiveDataAccess().getUslcData();
	}
}
