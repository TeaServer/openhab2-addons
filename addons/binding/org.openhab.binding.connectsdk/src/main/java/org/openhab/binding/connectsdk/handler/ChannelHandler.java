/**
 * Copyright (c) 2010-2015, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.connectsdk.handler;

import org.eclipse.smarthome.core.types.Command;

import com.connectsdk.device.ConnectableDevice;

/**
 * Channel Handler mediates between connect sdk device state changes and openhab channel events.
 * 
 * @author Sebastian Prehn
 * @since 1.8.0
 */
public interface ChannelHandler {

    public abstract void onReceiveCommand(ConnectableDevice device, Command command);

    public abstract void refreshSubscription(ConnectableDevice device, String channelId, ConnectSDKHandler handler);

    public abstract void removeAnySubscription(ConnectableDevice device);

    public abstract void onDeviceRemoved(final ConnectableDevice device, final String channelId,
            final ConnectSDKHandler handler);

    public abstract void onDeviceReady(final ConnectableDevice device, final String channelId,
            final ConnectSDKHandler handler);

}