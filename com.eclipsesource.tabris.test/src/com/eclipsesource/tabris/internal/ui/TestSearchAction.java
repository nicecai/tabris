/*******************************************************************************
 * Copyright (c) 2013 EclipseSource and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    EclipseSource - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.tabris.internal.ui;

import com.eclipsesource.tabris.ui.action.ProposalHandler;
import com.eclipsesource.tabris.ui.action.SearchAction;


public class TestSearchAction extends SearchAction {

  private boolean executed;

  @Override
  public void search( String query ) {
  }

  @Override
  public void execute() {
    executed = true;
  }

  public boolean wasExecuted() {
    return executed;
  }

  @Override
  public void modified( String query, ProposalHandler propoalHandler ) {
  }
}
