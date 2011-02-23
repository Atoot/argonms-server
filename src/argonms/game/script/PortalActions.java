/*
 * ArgonMS MapleStory server emulator written in Java
 * Copyright (C) 2011  GoldenKevin
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package argonms.game.script;

import argonms.character.Player;
import argonms.net.client.ClientSendOps;
import argonms.tools.output.LittleEndianByteArrayWriter;

/**
 *
 * @author GoldenKevin
 */
public class PortalActions {
	private Player p;

	public PortalActions(Player player) {
		this.p = player;
	}

	public Player getPlayer() {
		return p;
	}

	public void showHint(String hint, short width, short height) {
		p.getClient().getSession().send(writeHintBox(hint, width, height));
	}

	private static byte[] writeHintBox(String message, short width, short height) {
		if (width < 1)
			width = (short) Math.max(message.length() * 10, 40);
		if (height < 5)
			height = 5;

		LittleEndianByteArrayWriter lew = new LittleEndianByteArrayWriter(9
				+ message.length());
		lew.writeShort(ClientSendOps.PLAYER_HINT);
		lew.writeLengthPrefixedString(message);
		lew.writeShort(width);
		lew.writeShort(height);
		lew.writeBool(true);

		return lew.getBytes();
	}
}