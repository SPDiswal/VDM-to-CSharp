/*******************************************************************************
 * Copyright (c) 2009, 2011 Overture Team and others.
 *
 * Overture is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Overture is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Overture.  If not, see <http://www.gnu.org/licenses/>.
 * 	
 * The Overture Tool web-site: http://overturetool.org/
 *******************************************************************************/
package org.overture.ide.debug.core.dbgp.internal.packets;

import org.w3c.dom.Element;

public class DbgpNotifyPacket extends DbgpPacket
{
	private final String name;

	public DbgpNotifyPacket(Element content, String name)
	{
		super(content);

		if (name == null)
		{
			throw new IllegalArgumentException();
		}

		this.name = name;
	}

	public String getName()
	{
		return name;
	}
}
