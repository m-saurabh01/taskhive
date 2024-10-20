import React, {useState,useEffect} from 'react';
import { useNavigate } from 'react-router-dom';
import { callApi } from '../api/api-interceptor';


const TaskList = () => {
    const [tasks, setTasks] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const loadTasks = async () => {
            try {
                const tasksData = await callApi(
                    '/auth/tasks',
                    'GET',
                    null,
                    navigate
                );
                setTasks(tasksData);
            } catch (error) {
                console.error('Error loading tasks:', error);
            }
        };

        loadTasks();
    }, []);

    return (
        <div>
            <h2>Your Tasks</h2>
            <ul>
                {/* {tasks.map((task) => (
                    <li key={task.id}>
                        {task.title} - {task.status}
                    </li>
                ))} */<h3>{tasks?tasks:''}</h3>}
            </ul>
        </div>
    );
};

export default TaskList;
