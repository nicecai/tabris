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
package com.eclipsesource.tabris.ui;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.util.List;

import org.eclipse.rap.rwt.testfixture.Fixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.eclipsesource.tabris.internal.ui.ActionDescriptor;
import com.eclipsesource.tabris.internal.ui.PageDescriptor;
import com.eclipsesource.tabris.internal.ui.TestAction;
import com.eclipsesource.tabris.internal.ui.TestPage;
import com.eclipsesource.tabris.internal.ui.UITestUtil;


public class PageConfigurationTest {


  @Before
  public void setUp() {
    Fixture.setUp();
  }

  @After
  public void tearDown() {
    Fixture.tearDown();
  }

  @Test( expected = IllegalArgumentException.class )
  public void testFailsWithNullId() {
    new PageConfiguration( null, TestPage.class );
  }

  @Test( expected = IllegalArgumentException.class )
  public void testFailsWithEmptyId() {
    new PageConfiguration( "", TestPage.class );
  }

  @Test( expected = IllegalArgumentException.class )
  public void testFailsWithNullType() {
    new PageConfiguration( "foo", null );
  }

  @Test
  public void testCanCreateDescriptor() {
    PageConfiguration config = new PageConfiguration( "foo", TestPage.class ).setTopLevel( true );

    PageDescriptor descriptor = config.getAdapter( PageDescriptor.class );

    assertNotNull( descriptor );
  }

  @Test
  public void testSetsDefaultValues() {
    PageConfiguration config = new PageConfiguration( "foo", TestPage.class );

    PageDescriptor descriptor = config.getAdapter( PageDescriptor.class );

    assertEquals( "foo", descriptor.getId() );
    assertSame( TestPage.class, descriptor.getPageType() );
    assertFalse( descriptor.isTopLevel() );
    assertEquals( "", descriptor.getTitle() );
    assertNull( descriptor.getImage() );
    assertEquals( 0, descriptor.getPageStyle().length );
  }

  @Test
  public void testSetsTopLevel() {
    PageConfiguration config = new PageConfiguration( "foo", TestPage.class ).setTopLevel( true );

    PageDescriptor descriptor = config.getAdapter( PageDescriptor.class );

    assertTrue( descriptor.isTopLevel() );
  }

  @Test
  public void testSetsTitle() {
    PageConfiguration config = new PageConfiguration( "foo", TestPage.class ).setTitle( "bar" );

    PageDescriptor descriptor = config.getAdapter( PageDescriptor.class );

    assertEquals( "bar", descriptor.getTitle() );
  }

  @Test( expected = IllegalArgumentException.class )
  public void testSetTitleFailsWithNull() {
    new PageConfiguration( "foo", TestPage.class ).setTitle( null );
  }

  @Test
  public void testSetsImage() {
    InputStream image = UITestUtil.class.getResourceAsStream( "testImage.png" );
    PageConfiguration config = new PageConfiguration( "foo", TestPage.class ).setImage( image );

    PageDescriptor descriptor = config.getAdapter( PageDescriptor.class );

    assertArrayEquals( UITestUtil.getImageBytes(), descriptor.getImage() );
  }

  @Test( expected = IllegalArgumentException.class )
  public void testSetImageFailsWithNull() {
    new PageConfiguration( "foo", TestPage.class ).setImage( null );
  }

  @Test
  public void testSetsStyle() {
    PageConfiguration config = new PageConfiguration( "foo", TestPage.class ).setStyle( PageStyle.DEFAULT, PageStyle.FULLSCREEN );

    PageDescriptor descriptor = config.getAdapter( PageDescriptor.class );

    PageStyle[] pageStyle = descriptor.getPageStyle();
    assertEquals( 2, pageStyle.length );
    assertSame( PageStyle.DEFAULT, pageStyle[ 0 ] );
    assertSame( PageStyle.FULLSCREEN, pageStyle[ 1 ] );
  }

  @Test
  public void testAddsAction() {
    ActionConfiguration actionConfig = new ActionConfiguration( "bar", TestAction.class );
    PageConfiguration config = new PageConfiguration( "foo", TestPage.class ).addActionConfiguration( actionConfig );

    PageDescriptor descriptor = config.getAdapter( PageDescriptor.class );

    List<ActionDescriptor> actions = descriptor.getActions();
    assertEquals( 1, actions.size() );
    assertEquals( actions.get( 0 ).getId(), "bar" );
  }

  @Test( expected = IllegalArgumentException.class )
    public void testAddActionConfigurationFailsWithNullAction() {
      new PageConfiguration( "foo", TestPage.class ).addActionConfiguration( null );
    }

}
