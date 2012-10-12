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
package com.eclipsesource.tabris.camera;

import org.eclipse.rap.rwt.Adaptable;
import org.eclipse.rap.rwt.internal.protocol.ClientObjectAdapter;
import org.eclipse.rap.rwt.internal.protocol.IClientObjectAdapter;

import com.eclipsesource.tabris.camera.internal.CameraAdapter;
import com.eclipsesource.tabris.camera.internal.CameraSynchronizer;


/**
 * @since 0.8
 */
@SuppressWarnings("restriction")
public class Camera implements Adaptable {
  
  private ClientObjectAdapter clientObjectAdapter;
  private CameraAdapter cameraAdapter;

  public Camera( CameraOptions options ) {
    checkOptions( options );
    clientObjectAdapter = new ClientObjectAdapter( "c" );
    new CameraSynchronizer( this );
    cameraAdapter = new CameraAdapter();
    cameraAdapter.setOptions( options );
  }
  
  private void checkOptions( CameraOptions options ) {
    if( options == null ) {
      throw new IllegalArgumentException( "Camera Options must not be null, you can use CameraOptions.NONE." );
    }
  }

  public void takePicture( CameraCallback callback ) {
    cameraAdapter.setCallback( callback );
    cameraAdapter.open();
  }
  
  public void dispose() {
    cameraAdapter.dispose();
  }

  @SuppressWarnings("unchecked")
  public <T> T getAdapter( Class<T> adapter ) {
    T result = null;
    if( adapter == CameraAdapter.class ) {
      result = ( T )cameraAdapter;
    } else if( adapter == IClientObjectAdapter.class ) {
      result = ( T )clientObjectAdapter;
    }
    return result;
  }
  
}
