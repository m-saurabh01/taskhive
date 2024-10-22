import React, { useState } from 'react';
import { register } from '../services/authService';
import { useNavigate } from 'react-router-dom';
import Navbar from './Navbar';
import './register.css'

const Register = () => {
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState(null);
    const history = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await register({ name, email, password });
            history('/login'); // Redirect to login after registration
        } catch (error) {
            setError('Some error accured in registration: '+error.msg);
        }
    };

    return (
            <div className='register-container'>
                <Navbar />
                <form className='register_form' onSubmit={handleSubmit}>
                    <h2 className='register_heading'>Sign Up</h2>{' '}
                    {/* Register heading */}
                    <div className='input_box'>
                        <label htmlFor='name'>Name</label>
                        <input
                            type='text'
                            id='name'
                            placeholder='Enter your name'
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                            required
                        />
                    </div>
                    <div className='input_box'>
                        <label htmlFor='email'>Email</label>
                        <input
                            type='email'
                            id='email'
                            placeholder='Enter email address'
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                        />
                    </div>
                    <div className='input_box'>
                        <label htmlFor='password'>Password</label>
                        <input
                            type='password'
                            id='password'
                            placeholder='Enter your password'
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </div>
                    {error && (
                        <p className='err-msg' style={{ color: 'red' }}>
                            {error}
                        </p>
                    )}
                    <button type='submit'>Sign Up</button>
                    <p className='sign_in'>
                        Already have an account? <a href='/login'>Sign in</a>
                    </p>
                </form>
            </div>
            );
};

export default Register;
