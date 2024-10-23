import React from 'react';
import './footer.css'; // Your updated CSS for the footer

const Footer = () => {
    return (
        <footer className='footer' id='footer'>
            <div className='footer-container'>
                {/* Contact Us Section */}
                <div className='footer-section contact'>
                    <h3>Contact Us</h3>
                    <p>Address: 123 Tech Lane, Silicon Valley, CA 94043</p>
                    <p>Email: support@taskhiveapp.com</p>
                    <p>Phone: +1 (555) 123-4567</p>
                </div>

                {/* Services Section */}
                <div className='footer-section services'>
                    <h3>Services</h3>
                    <ul>
                        <li>Real-time collaboration</li>
                        <li>Task management</li>
                        <li>Role-based access control</li>
                        <li>Microservices architecture</li>
                        
                    </ul>
                </div>

                <div className='footer-section services-2'>
                    <ul>
                        <li>Kafka for real-time messaging</li>
                        <li>Redis caching</li>
                        <li>SQL/NoSQL databases</li>
                    </ul>
                </div>
            </div>

            <div className='footer-bottom'>
                <p>&copy; 2024 TaskHive | All Rights Reserved</p>
            </div>
        </footer>
    );
};

export default Footer;
