/**
 * Copyright (c) 2010-2015, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.connectsdk.handler;

import org.eclipse.smarthome.core.library.types.OnOffType;
import org.eclipse.smarthome.core.library.types.StringType;
import org.eclipse.smarthome.core.types.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.connectsdk.device.ConnectableDevice;
import com.connectsdk.service.capability.PowerControl;

/**
 * @author Sebastian Prehn
 * @since 1.8.0
 */
public class PowerControlPower extends AbstractChannelHandler<Void> {
    private static final Logger logger = LoggerFactory.getLogger(PowerControlPower.class);

    private PowerControl getControl(final ConnectableDevice device) {
        return device.getCapability(PowerControl.class);
    }

    @Override
    public void onReceiveCommand(final ConnectableDevice d, Command command) {
        OnOffType onOffType;
        if (command instanceof OnOffType) {
            onOffType = (OnOffType) command;
        } else if (command instanceof StringType) {
            onOffType = OnOffType.valueOf(command.toString());
        } else {
            logger.warn("only accept OnOffType");
            return;
        }

        if (OnOffType.ON.equals(onOffType) && d.hasCapabilities(PowerControl.On)) {
            getControl(d).powerOn(createDefaultResponseListener());
        }
        if (OnOffType.OFF.equals(onOffType) && d.hasCapabilities(PowerControl.Off)) {
            getControl(d).powerOff(createDefaultResponseListener());
        }

    }

    @Override
    public void onDeviceReady(ConnectableDevice device, final String channelId, final ConnectSDKHandler handler) {
        super.onDeviceReady(device, channelId, handler);
        handler.postUpdate(channelId, OnOffType.ON);
    }

    @Override
    public void onDeviceRemoved(ConnectableDevice device, final String channelId, final ConnectSDKHandler handler) {
        super.onDeviceReady(device, channelId, handler);
        handler.postUpdate(channelId, OnOffType.OFF);
    }

}