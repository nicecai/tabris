/*******************************************************************************
 * Copyright (c) 2012 EclipseSource and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    EclipseSource - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.tabris.internal;

import static com.eclipsesource.tabris.device.ClientDevice.Platform.ANDROID;
import static com.eclipsesource.tabris.device.ClientDevice.Platform.IOS;
import static com.eclipsesource.tabris.internal.Constants.THEME_ID_ANDROID;
import static com.eclipsesource.tabris.internal.Constants.THEME_ID_IOS;
import static com.eclipsesource.tabris.internal.Constants.THEME_ID_IOS6;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.client.Client;
import org.eclipse.rap.rwt.internal.client.ClientProvider;
import org.eclipse.rap.rwt.internal.theme.ThemeUtil;

import com.eclipsesource.tabris.device.ClientDevice.Platform;


@SuppressWarnings("restriction")
public class TabrisClientProvider implements ClientProvider, Serializable {

  @Override
  public boolean accept( HttpServletRequest request ) {
    Platform platform = DeviceUtil.getPlatform();
    boolean result = platform == ANDROID || platform == IOS;
    if( result ) {
      setThemeForPlatform( platform );
    }
    return result;
  }

  private void setThemeForPlatform( Platform platform ) {
    if( platform == IOS ) {
      String userAgent = RWT.getRequest().getHeader( Constants.USER_AGENT );
      if( userAgent.contains( "OS 6.1" ) ) {
        ThemeUtil.setCurrentThemeId( RWT.getUISession(), THEME_ID_IOS6 );
      } else {
        ThemeUtil.setCurrentThemeId( RWT.getUISession(), THEME_ID_IOS );
      }
    } else if( platform == ANDROID ) {
      ThemeUtil.setCurrentThemeId( RWT.getUISession(), THEME_ID_ANDROID );
    }
  }

  @Override
  public Client getClient() {
    return new TabrisClientImpl();
  }
}
