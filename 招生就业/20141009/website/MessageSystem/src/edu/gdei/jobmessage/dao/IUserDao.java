/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package edu.gdei.jobmessage.dao;

import java.util.List;

import edu.gdei.jobmessage.dao.common.IDao;
import edu.gdei.jobmessage.model.User;
import edu.gdei.jobmessage.util.UserNotFoundException;

/** 
 * User DAO (Data Access Object) interface. 
 *
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public interface IUserDao extends IDao<User>{

    public User getUser(String id);

    public User saveUser(User user);

    public void removeUser(String id);

    public boolean exists(String id);

    public List<User> getUsers();
    
    public List<User> getUsersByPage(int page, int pageSize);
    
    // 根据专业、生源地、性别查询用户，并推送消息
    public List<String> getUsersForNotify(String majorid, String addressid, int sex);

    public User getUserByUsername(String username) throws UserNotFoundException;

}
